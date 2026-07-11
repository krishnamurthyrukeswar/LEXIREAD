package com.lexiread.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lexiread.app.data.local.entity.ReadingProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReadingProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: ReadingProgressEntity)

    @Update
    suspend fun updateProgress(progress: ReadingProgressEntity)

    @Query("SELECT * FROM reading_progress WHERE book_id = :bookId")
    suspend fun getProgressByBook(bookId: String): ReadingProgressEntity?

    @Query("SELECT * FROM reading_progress WHERE book_id = :bookId")
    fun getProgressByBookFlow(bookId: String): Flow<ReadingProgressEntity?>

    @Query("SELECT * FROM reading_progress ORDER BY last_read_at DESC")
    fun getAllProgress(): Flow<List<ReadingProgressEntity>>

    @Query("SELECT SUM(total_reading_time_ms) FROM reading_progress")
    fun getTotalReadingTime(): Flow<Long?>

    @Query("DELETE FROM reading_progress WHERE book_id = :bookId")
    suspend fun deleteProgressByBook(bookId: String)
}
