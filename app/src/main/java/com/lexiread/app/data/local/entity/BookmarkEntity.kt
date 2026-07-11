package com.lexiread.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "bookmarks",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["book_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["book_id"])]
)
data class BookmarkEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "book_id")
    val bookId: String,

    @ColumnInfo(name = "page_number")
    val pageNumber: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "chapter")
    val chapter: String? = null,

    @ColumnInfo(name = "firebase_id")
    val firebaseId: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
