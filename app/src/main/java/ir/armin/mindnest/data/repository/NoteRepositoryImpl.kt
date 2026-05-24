package ir.armin.mindnest.data.repository

import android.util.Log
import ir.armin.mindnest.core.common.AppResult
import ir.armin.mindnest.core.common.appResultOf
import ir.armin.mindnest.data.local.database.daos.NoteDao
import ir.armin.mindnest.data.local.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun upsertNote(note: NoteEntity): AppResult<Long> = appResultOf(
        errorMessage = { "Failed to save note: ${it.message ?: "unknown error"}" }
    ) {
        val rowId = noteDao.upsertNote(note)
        if (rowId == 0L) {
            throw IllegalStateException("Upsert did not persist the note.")
        }
        rowId
    }.also { result ->
        if (result is AppResult.Error) {
            Log.e(TAG, "upsertNote failed", result.cause)
        }
    }

    override fun observeNotes(searchQuery: String): Flow<List<NoteEntity>> {
        return if (searchQuery.isBlank()) {
            noteDao.getAllNotes()
        } else {
            noteDao.searchNotes(searchQuery)
        }
    }

    override suspend fun deleteNoteById(noteId: Int): AppResult<Unit> = try {
        val rowsDeleted = noteDao.deleteNoteById(noteId)
        if (rowsDeleted > 0) {
            AppResult.Success(Unit)
        } else {
            AppResult.Error("Note not found or could not be deleted.")
        }
    } catch (e: Exception) {
        Log.e(TAG, "deleteNoteById failed for id=$noteId", e)
        AppResult.Error(
            message = "Failed to delete note: ${e.message ?: "unknown error"}",
            cause = e
        )
    }

    override fun getNoteById(id: Int): Flow<NoteEntity?> {
        return noteDao.getNoteById(id)
    }

    private companion object {
        const val TAG = "NoteRepository"
    }
}
