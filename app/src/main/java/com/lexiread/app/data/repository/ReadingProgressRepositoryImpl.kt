package com.lexiread.app.data.repository

import com.lexiread.app.data.local.dao.ReadingProgressDao
import com.lexiread.app.data.local.entity.ReadingProgressEntity
import com.lexiread.app.domain.model.ReadingProgress
import com.lexiread.app.domain.repository.ReadingProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReadingProgressRepositoryImpl @Inject constructor(
    private val progressDao: ReadingProgressDao
) : ReadingProgressRepository {

    override suspend fun getProgress(bookId: String): ReadingProgress? =
        progressDao.getProgressByBook(bookId)?.toDomain()

    override fun getProgressFlow(bookId: String): Flow<ReadingProgress?> =
        progressDao.getProgressByBookFlow(bookId).map { it?.toDomain() }

    override fun getAllProgress(): Flow<List<ReadingProgress>> =
        progressDao.getAllProgress().map { list -> list.map { it.toDomain() } }

    override suspend fun saveProgress(progress: ReadingProgress) {
        val existing = progressDao.getProgressByBook(progress.bookId)
        if (existing != null) {
            progressDao.updateProgress(
                existing.copy(
                    currentPage = progress.currentPage,
                    totalPages = progress.totalPages,
                    percentage = progress.percentage,
                    lastReadAt = System.currentTimeMillis(),
                    totalReadingTimeMs = progress.totalReadingTimeMs,
                    updatedAt = System.currentTimeMillis()
                )
            )
        } else {
            progressDao.insertProgress(
                ReadingProgressEntity(
                    id = UUID.randomUUID().toString(),
                    bookId = progress.bookId,
                    currentPage = progress.currentPage,
                    totalPages = progress.totalPages,
                    percentage = progress.percentage,
                    lastReadAt = System.currentTimeMillis(),
                    totalReadingTimeMs = progress.totalReadingTimeMs,
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
    }

    override suspend fun deleteProgress(bookId: String) =
        progressDao.deleteProgressByBook(bookId)

    override fun getTotalReadingTime(): Flow<Long?> =
        progressDao.getTotalReadingTime()

    private fun ReadingProgressEntity.toDomain() = ReadingProgress(
        id = id,
        bookId = bookId,
        currentPage = currentPage,
        totalPages = totalPages,
        percentage = percentage,
        lastReadAt = lastReadAt,
        totalReadingTimeMs = totalReadingTimeMs
    )
}
