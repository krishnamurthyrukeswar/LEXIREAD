package com.lexiread.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lexiread.app.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE book_id = :bookId ORDER BY page_number ASC")
    fun getNotesByBook(bookId: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes ORDER BY created_at DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: String): NoteEntity?

    @Query("SELECT * FROM notes WHERE highlight_id = :highlightId")
    suspend fun getNoteByHighlight(highlightId: String): NoteEntity?

    @Query("SELECT * FROM notes WHERE content LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    @Query("DELETE FROM notes WHERE book_id = :bookId")
    suspend fun deleteNotesByBook(bookId: String)
}
