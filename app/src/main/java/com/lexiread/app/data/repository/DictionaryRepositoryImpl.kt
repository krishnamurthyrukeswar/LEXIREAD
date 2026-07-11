package com.lexiread.app.data.repository

import com.lexiread.app.data.local.dao.DictionaryCacheDao
import com.lexiread.app.data.local.dictionary.DictionaryDatabaseHelper
import com.lexiread.app.data.local.entity.DictionaryCacheEntity
import com.lexiread.app.domain.repository.DictionaryRepository
import com.lexiread.app.domain.repository.DictionaryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryHelper: DictionaryDatabaseHelper,
    private val cacheDao: DictionaryCacheDao
) : DictionaryRepository {

    override suspend fun getDefinition(word: String): DictionaryResult? =
        withContext(Dispatchers.IO) {
            val cleanWord = stripPunctuation(word).lowercase().trim()
            if (cleanWord.isBlank()) return@withContext null

            // 1. Check Room cache first
            val cached = cacheDao.getCachedDefinition(cleanWord)
            if (cached != null) {
                // Update timestamp for recently-used ordering
                cacheDao.insertCache(cached.copy(timestamp = System.currentTimeMillis()))
                return@withContext parseCachedResult(cached)
            }

            // 2. Try exact match in dictionary.db
            var rawDefinition = dictionaryHelper.lookupWord(cleanWord)

            // 3. If not found, try stemmed forms
            if (rawDefinition == null) {
                for (stem in generateStems(cleanWord)) {
                    rawDefinition = dictionaryHelper.lookupWord(stem)
                    if (rawDefinition != null) break
                }
            }

            if (rawDefinition == null) return@withContext null

            // 4. Parse definition
            val result = parseDefinition(cleanWord, rawDefinition)

            // 5. Cache in Room
            cacheDao.insertCache(
                DictionaryCacheEntity(
                    word = cleanWord,
                    definition = rawDefinition,
                    partOfSpeech = result.partOfSpeech,
                    phonetic = result.phonetic,
                    timestamp = System.currentTimeMillis()
                )
            )
            cacheDao.pruneOldEntries()

            result
        }

    override fun getRecentLookups(limit: Int): Flow<List<DictionaryCacheEntity>> =
        cacheDao.getRecentLookups(limit)

    override suspend fun clearCache() = cacheDao.clearCache()

    // ═══════════════════════════════════
    //  Text Processing Helpers
    // ═══════════════════════════════════

    /**
     * Strips punctuation from both ends of a word.
     */
    private fun stripPunctuation(word: String): String =
        word.replace(Regex("^[^a-zA-Z]+|[^a-zA-Z]+$"), "")

    /**
     * Generates possible stem forms by removing common English suffixes.
     * Ordered from most specific to least specific.
     */
    private fun generateStems(word: String): List<String> {
        val stems = mutableListOf<String>()

        val suffixes = listOf(
            "ying" to "y",     // studying → study
            "ies" to "y",     // studies → study
            "ied" to "y",     // studied → study
            "ving" to "ve",   // moving → move
            "ting" to "t",    // hitting → hit (double consonant)
            "ning" to "n",    // running → run
            "ming" to "m",    // swimming → swim
            "ping" to "p",    // stopping → stop
            "ging" to "g",    // digging → dig
            "bing" to "b",    // robbing → rob
            "ding" to "d",    // adding → add
            "ling" to "l",    // compelling → compel
            "sing" to "s",    // missing → miss (but also parsing → parse)
            "zing" to "z",    // buzzing → buzz
            "ting" to "te",   // creating → create
            "cing" to "ce",   // dancing → dance
            "ging" to "ge",   // changing → change
            "ness" to "",     // happiness → happi (then try happy)
            "ment" to "",     // development → develop
            "able" to "",     // readable → read
            "ible" to "",     // visible → vis
            "tion" to "t",    // creation → creat (then try create)
            "sion" to "d",    // expansion → expand
            "ally" to "al",   // basically → basic + al
            "ously" to "ous", // seriously → serious
            "fully" to "ful", // carefully → careful
            "ily" to "y",     // happily → happy
            "ing" to "e",     // making → make
            "ing" to "",      // running → runn (try run too)
            "ation" to "e",   // creation → create
            "ated" to "ate",  // created → create
            "ates" to "ate",  // creates → create
            "ting" to "",     // creating → crea
            "ness" to "",     // sadness → sad
            "ment" to "",     // movement → move
            "ful" to "",      // beautiful → beauti
            "less" to "",     // careless → care
            "ed" to "",       // jumped → jump
            "ed" to "e",      // created → create
            "er" to "",       // bigger → bigg
            "er" to "e",      // nicer → nice
            "est" to "",      // biggest → bigg
            "est" to "e",     // nicest → nice
            "ly" to "",       // quickly → quick
            "es" to "",       // boxes → box
            "s" to "",        // cats → cat
        )

        for ((suffix, replacement) in suffixes) {
            if (word.endsWith(suffix) && word.length > suffix.length + 1) {
                val stem = word.dropLast(suffix.length) + replacement
                if (stem.isNotEmpty() && stem !in stems) {
                    stems.add(stem)
                }
            }
        }

        return stems
    }

    /**
     * Parses a raw definition string to extract part of speech, phonetic,
     * and separate multiple definitions.
     *
     * Expected formats:
     *   "(noun) definition text"
     *   "/fəˈnetɪk/ (verb) definition text"
     *   "1. first definition 2. second definition"
     */
    private fun parseDefinition(word: String, raw: String): DictionaryResult {
        var definition = raw.trim()
        var partOfSpeech: String? = null
        var phonetic: String? = null

        // Extract phonetic: /.../ at the start
        val phoneticMatch = Regex("^/([^/]+)/\\s*").find(definition)
        if (phoneticMatch != null) {
            phonetic = "/${phoneticMatch.groupValues[1]}/"
            definition = definition.removePrefix(phoneticMatch.value).trim()
        }

        // Extract part of speech: (noun), (verb), (adj), etc.
        val posMatch = Regex("^\\(([^)]+)\\)\\s*").find(definition)
        if (posMatch != null) {
            partOfSpeech = posMatch.groupValues[1].trim()
            definition = definition.removePrefix(posMatch.value).trim()
        }

        // Split multiple definitions (by numbered list or newlines)
        val definitions = mutableListOf<String>()
        val numberedPattern = Regex("\\d+\\.\\s+")
        val parts = definition.split(numberedPattern).filter { it.isNotBlank() }

        if (parts.size > 1) {
            definitions.addAll(parts.map { it.trim() })
            definition = definitions.first()
        } else {
            // Try splitting by semicolons
            val semiParts = definition.split(";").map { it.trim() }.filter { it.isNotBlank() }
            if (semiParts.size > 1) {
                definitions.addAll(semiParts)
                definition = definitions.first()
            }
        }

        return DictionaryResult(
            word = word,
            definition = definition,
            partOfSpeech = partOfSpeech,
            phonetic = phonetic,
            alternateDefinitions = if (definitions.size > 1) definitions.drop(1) else emptyList()
        )
    }

    private fun parseCachedResult(cached: DictionaryCacheEntity): DictionaryResult {
        // Re-parse to get alternate definitions
        return parseDefinition(cached.word, cached.definition)
    }
}
