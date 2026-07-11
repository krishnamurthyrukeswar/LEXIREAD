package com.lexiread.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lexiread.app.data.local.entity.DictionaryCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryCacheDao {

    @Query("SELECT * FROM dictionary_cache WHERE word = :word COLLATE NOCASE LIMIT 1")
    suspend fun getCachedDefinition(word: String): DictionaryCacheEntity?

    @Query("SELECT * FROM dictionary_cache ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentLookups(limit: Int = 100): Flow<List<DictionaryCacheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(entry: DictionaryCacheEntity)

    @Query("DELETE FROM dictionary_cache WHERE word NOT IN (SELECT word FROM dictionary_cache ORDER BY timestamp DESC LIMIT 100)")
    suspend fun pruneOldEntries()

    @Query("SELECT COUNT(*) FROM dictionary_cache")
    suspend fun getCacheCount(): Int

    @Query("DELETE FROM dictionary_cache")
    suspend fun clearCache()
}
