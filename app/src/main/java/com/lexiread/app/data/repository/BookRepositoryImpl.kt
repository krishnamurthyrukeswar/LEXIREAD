package com.lexiread.app.data.repository

import android.net.Uri
import com.lexiread.app.data.local.dao.BookDao
import com.lexiread.app.data.local.file.FileManager
import com.lexiread.app.data.mapper.BookMapper.toDomain
import com.lexiread.app.data.mapper.BookMapper.toDomainList
import com.lexiread.app.data.mapper.BookMapper.toEntity
import com.lexiread.app.data.remote.api.GoogleBooksApi
import com.lexiread.app.domain.model.Book
import com.lexiread.app.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val bookDao: BookDao,
    private val googleBooksApi: GoogleBooksApi,
    private val fileManager: FileManager
) : BookRepository {

    override fun getAllBooks(): Flow<List<Book>> =
        bookDao.getAllBooks().map { it.toDomainList() }

    override fun getRecentBooks(limit: Int): Flow<List<Book>> =
        bookDao.getRecentlyReadBooks(limit).map { it.toDomainList() }

    override fun getFavoriteBooks(): Flow<List<Book>> =
        bookDao.getFavoriteBooks().map { it.toDomainList() }

    override fun searchBooks(query: String): Flow<List<Book>> =
        bookDao.searchBooks(query).map { it.toDomainList() }

    override fun getBookCount(): Flow<Int> = bookDao.getBookCount()

    override suspend fun getBookById(bookId: String): Book? =
        bookDao.getBookById(bookId)?.toDomain()

    override suspend fun insertBook(book: Book) =
        bookDao.insertBook(book.toEntity())

    override suspend fun updateBook(book: Book) =
        bookDao.updateBook(book.toEntity())

    override suspend fun deleteBook(book: Book) {
        // Delete local file
        fileManager.deleteFile(book.filePath)
        book.coverImagePath?.let { fileManager.deleteFile(it) }
        // Delete from DB
        bookDao.deleteBook(book.toEntity())
    }

    override suspend fun toggleFavorite(bookId: String, isFavorite: Boolean) =
        bookDao.updateFavoriteStatus(bookId, isFavorite)

    override suspend fun updateLastOpened(bookId: String) =
        bookDao.updateLastOpened(bookId)

    override suspend fun fetchCoverUrl(title: String, author: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val query = "intitle:${title}+inauthor:${author}"
                val response = googleBooksApi.searchBooks(query)
                val imageLinks = response.items?.firstOrNull()?.volumeInfo?.imageLinks
                // Prefer thumbnail over smallThumbnail; convert http → https
                (imageLinks?.thumbnail ?: imageLinks?.smallThumbnail)
                    ?.replace("http://", "https://")
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    /**
     * Imports a book from a content URI:
     * 1. Copies the file to internal storage
     * 2. Extracts metadata from filename
     * 3. Fetches cover from Google Books API
     * 4. Saves to Room
     * @return the created [Book] or null on failure
     */
    override suspend fun importBook(uri: Uri): Book? = withContext(Dispatchers.IO) {
        val rawName = fileManager.getFileName(uri) ?: return@withContext null
        var extension = rawName.substringAfterLast('.', "")
        
        if (extension.isEmpty() || extension == rawName) {
            val mime = fileManager.getMimeType(uri)
            extension = when (mime) {
                "application/pdf" -> "pdf"
                "application/epub+zip" -> "epub"
                "text/plain" -> "txt"
                else -> ""
            }
        }
        extension = extension.uppercase()

        val supportedFormats = listOf("PDF", "EPUB", "TXT")
        if (extension !in supportedFormats) return@withContext null

        val bookId = UUID.randomUUID().toString()
        val internalFileName = "${bookId}.${extension.lowercase()}"

        val filePath = fileManager.copyFileToInternal(uri, internalFileName)
            ?: return@withContext null

        // Extract title and author from filename
        val nameWithoutExtension = rawName.substringBeforeLast('.')
        val parts = parseFileName(nameWithoutExtension)
        val title = parts.first
        val author = parts.second

        val fileSize = fileManager.getFileSize(uri)

        // Fetch cover URL from Google Books
        val coverUrl = try {
            fetchCoverUrl(title, author)
        } catch (e: Exception) {
            null
        }

        val book = Book(
            id = bookId,
            title = title,
            author = author,
            format = extension,
            filePath = filePath,
            coverImagePath = coverUrl,
            fileSize = fileSize,
            totalPages = 0,
            addedAt = System.currentTimeMillis()
        )

        insertBook(book)
        book
    }

    /**
     * Parses a filename to extract title and author.
     * Common patterns: "Title - Author", "Title_Author", "Title (Author)"
     */
    private fun parseFileName(name: String): Pair<String, String> {
        // Try "Title - Author"
        if (name.contains(" - ")) {
            val parts = name.split(" - ", limit = 2)
            return Pair(parts[0].trim(), parts[1].trim())
        }
        // Try "Title (Author)"
        val parenMatch = Regex("(.+)\\s*\\((.+)\\)").find(name)
        if (parenMatch != null) {
            return Pair(
                parenMatch.groupValues[1].trim(),
                parenMatch.groupValues[2].trim()
            )
        }
        // Fallback: use whole name as title, unknown author
        return Pair(
            name.replace('_', ' ').replace('.', ' ').trim(),
            "Unknown Author"
        )
    }
}
