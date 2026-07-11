package com.lexiread.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "reading_progress",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["book_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["book_id"], unique = true)]
)
data class ReadingProgressEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "book_id")
    val bookId: String,

    @ColumnInfo(name = "current_page")
    val currentPage: Int = 0,

    @ColumnInfo(name = "total_pages")
    val totalPages: Int = 0,

    @ColumnInfo(name = "percentage")
    val percentage: Float = 0f,

    @ColumnInfo(name = "last_read_at")
    val lastReadAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "total_reading_time_ms")
    val totalReadingTimeMs: Long = 0L,

    @ColumnInfo(name = "firebase_id")
    val firebaseId: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)
