package ir.armin.mindnest.data.repository

import ir.armin.mindnest.core.common.AppResult
import ir.armin.mindnest.data.local.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun upsertNote(note: NoteEntity): AppResult<Long>

    fun observeNotes(searchQuery: String): Flow<List<NoteEntity>>

    suspend fun deleteNoteById(noteId: Int): AppResult<Unit>

    fun getNoteById(id: Int): Flow<NoteEntity?>
}
