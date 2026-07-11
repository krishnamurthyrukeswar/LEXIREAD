package com.lexiread.app.data.local.dictionary

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages the offline dictionary SQLite database.
 *
 * On first launch, copies `dictionary.db` from assets/ to the app's
 * internal database directory, then opens it in read-only mode.
 *
 * Expected schema: CREATE TABLE dictionary (word TEXT PRIMARY KEY, definition TEXT)
 */
@Singleton
class DictionaryDatabaseHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dbName = "dictionary.db"
    private var database: SQLiteDatabase? = null

    /**
     * Returns the read-only SQLiteDatabase, copying from assets on first call.
     */
    suspend fun getDatabase(): SQLiteDatabase? = withContext(Dispatchers.IO) {
        if (database?.isOpen == true) return@withContext database

        val dbFile = context.getDatabasePath(dbName)

        if (!dbFile.exists()) {
            val copied = copyDatabaseFromAssets(dbFile)
            if (!copied) return@withContext null
        }

        try {
            database = SQLiteDatabase.openDatabase(
                dbFile.absolutePath,
                null,
                SQLiteDatabase.OPEN_READONLY
            )
            database
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Copies dictionary.db from assets/ to the databases directory.
     */
    private fun copyDatabaseFromAssets(destFile: File): Boolean {
        return try {
            destFile.parentFile?.mkdirs()

            context.assets.open(dbName).use { inputStream ->
                FileOutputStream(destFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Queries the dictionary for a word definition.
     * Returns the raw definition string or null if not found.
     */
    suspend fun lookupWord(word: String): String? = withContext(Dispatchers.IO) {
        val db = getDatabase() ?: return@withContext null

        try {
            val cursor = db.rawQuery(
                "SELECT definition FROM dictionary WHERE word = ? COLLATE NOCASE",
                arrayOf(word)
            )
            cursor.use {
                if (it.moveToFirst()) {
                    it.getString(0)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun close() {
        database?.close()
        database = null
    }
}
