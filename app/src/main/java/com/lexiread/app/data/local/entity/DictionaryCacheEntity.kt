package com.lexiread.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Caches recent dictionary lookups in Room.
 * Limited to 100 entries (oldest are pruned).
 */
@Entity(tableName = "dictionary_cache")
data class DictionaryCacheEntity(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "definition")
    val definition: String,

    @ColumnInfo(name = "part_of_speech")
    val partOfSpeech: String? = null,

    @ColumnInfo(name = "phonetic")
    val phonetic: String? = null,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)
