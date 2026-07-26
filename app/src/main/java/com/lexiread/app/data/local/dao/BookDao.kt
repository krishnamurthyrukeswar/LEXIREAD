package com.lexiread.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lexiread.app.data.local.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Update
    suspend fun updateBook(book: BookEntity)

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("SELECT * FROM books ORDER BY added_at DESC")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BookEntity?

    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookByIdFlow(bookId: String): Flow<BookEntity?>

    @Query("SELECT * FROM books WHERE is_favorite = 1 ORDER BY added_at DESC")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books ORDER BY last_opened_at DESC LIMIT :limit")
    fun getRecentlyReadBooks(limit: Int = 10): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%'")
    fun searchBooks(query: String): Flow<List<BookEntity>>

    @Query("UPDATE books SET is_favorite = :isFavorite WHERE id = :bookId")
    suspend fun updateFavoriteStatus(bookId: String, isFavorite: Boolean)

    @Query("UPDATE books SET last_opened_at = :timestamp WHERE id = :bookId")
    suspend fun updateLastOpened(bookId: String, timestamp: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM books")
    fun getBookCount(): Flow<Int>

    @Query("SELECT * FROM books WHERE title = :title LIMIT 1")
    suspend fun findBookByTitle(title: String): BookEntity?
}
