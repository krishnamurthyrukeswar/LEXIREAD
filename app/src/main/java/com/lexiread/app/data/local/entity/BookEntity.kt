package com.lexiread.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "format")
    val format: String, // "PDF" or "EPUB"

    @ColumnInfo(name = "file_path")
    val filePath: String,

    @ColumnInfo(name = "cover_image_path")
    val coverImagePath: String? = null,

    @ColumnInfo(name = "file_size")
    val fileSize: Long = 0L,

    @ColumnInfo(name = "total_pages")
    val totalPages: Int = 0,

    @ColumnInfo(name = "language")
    val language: String? = null,

    @ColumnInfo(name = "publisher")
    val publisher: String? = null,

    @ColumnInfo(name = "isbn")
    val isbn: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "firebase_id")
    val firebaseId: String? = null,

    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "last_opened_at")
    val lastOpenedAt: Long? = null
)
