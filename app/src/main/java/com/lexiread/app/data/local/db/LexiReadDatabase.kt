package com.lexiread.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lexiread.app.data.local.dao.BookDao
import com.lexiread.app.data.local.dao.BookmarkDao
import com.lexiread.app.data.local.dao.DictionaryCacheDao
import com.lexiread.app.data.local.dao.HighlightDao
import com.lexiread.app.data.local.dao.NoteDao
import com.lexiread.app.data.local.dao.ReadingProgressDao
import com.lexiread.app.data.local.entity.BookEntity
import com.lexiread.app.data.local.entity.BookmarkEntity
import com.lexiread.app.data.local.entity.DictionaryCacheEntity
import com.lexiread.app.data.local.entity.HighlightEntity
import com.lexiread.app.data.local.entity.NoteEntity
import com.lexiread.app.data.local.entity.ReadingProgressEntity

@Database(
    entities = [
        BookEntity::class,
        HighlightEntity::class,
        NoteEntity::class,
        BookmarkEntity::class,
        ReadingProgressEntity::class,
        DictionaryCacheEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class LexiReadDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun highlightDao(): HighlightDao
    abstract fun noteDao(): NoteDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun readingProgressDao(): ReadingProgressDao
    abstract fun dictionaryCacheDao(): DictionaryCacheDao

    companion object {
        const val DATABASE_NAME = "lexiread_database"
    }
}
