package com.lexiread.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lexiread.app.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)

    @Query("SELECT * FROM bookmarks WHERE book_id = :bookId ORDER BY page_number ASC")
    fun getBookmarksByBook(bookId: String): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks ORDER BY created_at DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE id = :bookmarkId")
    suspend fun getBookmarkById(bookmarkId: String): BookmarkEntity?

    @Query("SELECT * FROM bookmarks WHERE book_id = :bookId AND page_number = :pageNumber LIMIT 1")
    suspend fun getBookmarkByPage(bookId: String, pageNumber: Int): BookmarkEntity?

    @Query("DELETE FROM bookmarks WHERE book_id = :bookId")
    suspend fun deleteBookmarksByBook(bookId: String)
}
