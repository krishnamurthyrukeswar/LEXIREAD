package com.lexiread.app.data.local.file

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages file operations for imported books.
 * Books are copied from external URIs into app-private internal storage.
 */
@Singleton
class FileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val booksDir: File
        get() = File(context.filesDir, "books").also { if (!it.exists()) it.mkdirs() }

    private val coversDir: File
        get() = File(context.filesDir, "covers").also { if (!it.exists()) it.mkdirs() }

    /**
     * Copies a file from a content URI to internal storage.
     * Returns the destination file path or null on failure.
     */
    fun copyFileToInternal(uri: Uri, fileName: String): String? {
        return try {
            val destFile = File(booksDir, fileName)
            context.contentResolver.openInputStream(uri)?.use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            destFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Returns the file size in bytes from a content URI.
     */
    fun getFileSize(uri: Uri): Long {
        return try {
            context.contentResolver.openInputStream(uri)?.use {
                it.available().toLong()
            } ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Extracts the display name from a content URI.
     */
    fun getFileName(uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                if (nameIndex >= 0) it.getString(nameIndex) else null
            } else null
        }
    }

    /**
     * Gets the MIME type from a content URI.
     */
    fun getMimeType(uri: Uri): String? {
        return context.contentResolver.getType(uri)
    }

    /**
     * Deletes a book file from internal storage.
     */
    fun deleteFile(filePath: String): Boolean {
        return try {
            File(filePath).delete()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Returns the cover image file path for a given book ID.
     */
    fun getCoverPath(bookId: String): String {
        return File(coversDir, "${bookId}.jpg").absolutePath
    }

    /**
     * Checks if a file exists at the given path.
     */
    fun fileExists(filePath: String): Boolean = File(filePath).exists()
}
