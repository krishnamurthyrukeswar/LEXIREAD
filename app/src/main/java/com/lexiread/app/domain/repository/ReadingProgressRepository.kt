package com.lexiread.app.domain.repository

import com.lexiread.app.domain.model.ReadingProgress
import kotlinx.coroutines.flow.Flow

/**
 * Domain-layer repository contract for reading progress operations.
 */
interface ReadingProgressRepository {

    suspend fun getProgress(bookId: String): ReadingProgress?

    fun getProgressFlow(bookId: String): Flow<ReadingProgress?>

    fun getAllProgress(): Flow<List<ReadingProgress>>

    suspend fun saveProgress(progress: ReadingProgress)

    suspend fun deleteProgress(bookId: String)

    fun getTotalReadingTime(): Flow<Long?>
}
