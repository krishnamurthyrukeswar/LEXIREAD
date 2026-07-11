package com.lexiread.app.di

import android.content.Context
import androidx.room.Room
import com.lexiread.app.data.local.dao.BookDao
import com.lexiread.app.data.local.dao.BookmarkDao
import com.lexiread.app.data.local.dao.HighlightDao
import com.lexiread.app.data.local.dao.NoteDao
import com.lexiread.app.data.local.dao.ReadingProgressDao
import com.lexiread.app.data.local.dao.DictionaryCacheDao
import com.lexiread.app.data.local.db.LexiReadDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LexiReadDatabase {
        return Room.databaseBuilder(
            context,
            LexiReadDatabase::class.java,
            LexiReadDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBookDao(database: LexiReadDatabase): BookDao = database.bookDao()

    @Provides
    fun provideHighlightDao(database: LexiReadDatabase): HighlightDao = database.highlightDao()

    @Provides
    fun provideNoteDao(database: LexiReadDatabase): NoteDao = database.noteDao()

    @Provides
    fun provideBookmarkDao(database: LexiReadDatabase): BookmarkDao = database.bookmarkDao()

    @Provides
    fun provideReadingProgressDao(database: LexiReadDatabase): ReadingProgressDao =
        database.readingProgressDao()

    @Provides
    fun provideDictionaryCacheDao(database: LexiReadDatabase): DictionaryCacheDao =
        database.dictionaryCacheDao()
}
