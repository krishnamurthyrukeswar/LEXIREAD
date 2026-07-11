package com.lexiread.app.data.mapper

import com.lexiread.app.data.local.entity.BookEntity
import com.lexiread.app.domain.model.Book

/**
 * Mapper functions between BookEntity (Room) and Book (Domain).
 */
object BookMapper {

    fun BookEntity.toDomain(): Book = Book(
        id = id,
        title = title,
        author = author,
        format = format,
        filePath = filePath,
        coverImagePath = coverImagePath,
        fileSize = fileSize,
        totalPages = totalPages,
        language = language,
        publisher = publisher,
        isbn = isbn,
        description = description,
        isFavorite = isFavorite,
        addedAt = addedAt,
        lastOpenedAt = lastOpenedAt
    )

    fun Book.toEntity(): BookEntity = BookEntity(
        id = id,
        title = title,
        author = author,
        format = format,
        filePath = filePath,
        coverImagePath = coverImagePath,
        fileSize = fileSize,
        totalPages = totalPages,
        language = language,
        publisher = publisher,
        isbn = isbn,
        description = description,
        isFavorite = isFavorite,
        addedAt = addedAt,
        lastOpenedAt = lastOpenedAt
    )

    fun List<BookEntity>.toDomainList(): List<Book> = map { it.toDomain() }
}
