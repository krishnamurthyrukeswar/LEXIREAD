package com.lexiread.app.domain.model

/**
 * Domain model for a Book. Maps from BookEntity but is
 * independent of Room annotations.
 */
data class Book(
    val id: String,
    val title: String,
    val author: String,
    val format: String,
    val filePath: String,
    val coverImagePath: String? = null,
    val fileSize: Long = 0L,
    val totalPages: Int = 0,
    val language: String? = null,
    val publisher: String? = null,
    val isbn: String? = null,
    val description: String? = null,
    val isFavorite: Boolean = false,
    val addedAt: Long = 0L,
    val lastOpenedAt: Long? = null
)
