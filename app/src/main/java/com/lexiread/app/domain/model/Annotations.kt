package com.lexiread.app.domain.model

data class Highlight(
    val id: String,
    val bookId: String,
    val text: String,
    val color: String,
    val pageNumber: Int,
    val charStart: Int = 0,
    val charEnd: Int = 0,
    val chapter: String? = null,
    val createdAt: Long = 0L
)

data class Note(
    val id: String,
    val bookId: String,
    val highlightId: String? = null,
    val content: String,
    val pageNumber: Int,
    val chapter: String? = null,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)

data class Bookmark(
    val id: String,
    val bookId: String,
    val pageNumber: Int,
    val title: String,
    val chapter: String? = null,
    val createdAt: Long = 0L
)

data class ReadingProgress(
    val id: String,
    val bookId: String,
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val percentage: Float = 0f,
    val lastReadAt: Long = 0L,
    val totalReadingTimeMs: Long = 0L
)
