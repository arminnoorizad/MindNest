package ir.armin.mindnest.data.local.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.armin.mindnest.data.local.database.NoteDatabase
import ir.armin.mindnest.data.local.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: NoteEntity): Long

    @Query("SELECT * FROM ${NoteDatabase.TABLE_NAME} ORDER BY id DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM ${NoteDatabase.TABLE_NAME} WHERE id = :noteId")
    fun getNoteById(noteId: Int): Flow<NoteEntity?>

    @Query("DELETE FROM ${NoteDatabase.TABLE_NAME} WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int): Int

    @Query(
        """
        SELECT * FROM ${NoteDatabase.TABLE_NAME}
        WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'
        ORDER BY id DESC
        """
    )
    fun searchNotes(query: String): Flow<List<NoteEntity>>
}
