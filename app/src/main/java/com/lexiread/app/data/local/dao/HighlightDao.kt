package com.lexiread.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lexiread.app.data.local.entity.HighlightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HighlightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighlight(highlight: HighlightEntity)

    @Delete
    suspend fun deleteHighlight(highlight: HighlightEntity)

    @Query("SELECT * FROM highlights WHERE book_id = :bookId ORDER BY page_number ASC, char_start ASC")
    fun getHighlightsByBook(bookId: String): Flow<List<HighlightEntity>>

    @Query("SELECT * FROM highlights ORDER BY created_at DESC")
    fun getAllHighlights(): Flow<List<HighlightEntity>>

    @Query("SELECT * FROM highlights WHERE id = :highlightId")
    suspend fun getHighlightById(highlightId: String): HighlightEntity?

    @Query("SELECT * FROM highlights WHERE book_id = :bookId AND page_number = :pageNumber")
    fun getHighlightsByPage(bookId: String, pageNumber: Int): Flow<List<HighlightEntity>>

    @Query("DELETE FROM highlights WHERE book_id = :bookId")
    suspend fun deleteHighlightsByBook(bookId: String)

    @Query("SELECT COUNT(*) FROM highlights WHERE book_id = :bookId")
    fun getHighlightCountByBook(bookId: String): Flow<Int>
}
