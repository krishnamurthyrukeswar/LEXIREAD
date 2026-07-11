package com.lexiread.app.domain.repository

import com.lexiread.app.data.local.entity.DictionaryCacheEntity
import kotlinx.coroutines.flow.Flow

/**
 * Domain-layer contract for dictionary operations.
 * Works 100% offline using the bundled SQLite dictionary.
 */
interface DictionaryRepository {

    /**
     * Look up a word definition. Tries exact match first,
     * then stems the word (removes -ing, -ed, -s, -ly, -er, -est).
     * Results are cached in Room automatically.
     */
    suspend fun getDefinition(word: String): DictionaryResult?

    /**
     * Recent lookups from the cache, ordered by timestamp descending.
     */
    fun getRecentLookups(limit: Int = 100): Flow<List<DictionaryCacheEntity>>

    /**
     * Clear the lookup cache.
     */
    suspend fun clearCache()
}

data class DictionaryResult(
    val word: String,
    val definition: String,
    val partOfSpeech: String? = null,
    val phonetic: String? = null,
    val alternateDefinitions: List<String> = emptyList()
)
