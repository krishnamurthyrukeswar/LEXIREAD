package com.lexiread.app.domain.repository

import com.lexiread.app.domain.model.Book
import kotlinx.coroutines.flow.Flow

/**
 * Domain-layer repository contract for book operations.
 * Implemented by [com.lexiread.app.data.repository.BookRepositoryImpl].
 */
interface BookRepository {

    fun getAllBooks(): Flow<List<Book>>

    fun getRecentBooks(limit: Int = 10): Flow<List<Book>>

    fun getFavoriteBooks(): Flow<List<Book>>

    fun searchBooks(query: String): Flow<List<Book>>

    fun getBookCount(): Flow<Int>

    suspend fun getBookById(bookId: String): Book?

    suspend fun insertBook(book: Book)

    suspend fun updateBook(book: Book)

    suspend fun deleteBook(book: Book)

    suspend fun toggleFavorite(bookId: String, isFavorite: Boolean)

    suspend fun updateLastOpened(bookId: String)

    suspend fun fetchCoverUrl(title: String, author: String): String?

    suspend fun importBook(uri: android.net.Uri): Book?
}
