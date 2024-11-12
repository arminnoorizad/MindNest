package com.example.purpleNote.model.repository

import com.example.purpleNote.model.database.NoteDao
import com.example.purpleNote.model.database.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {


    suspend fun upsertNote(note: NoteEntity) {
        noteDao.upsertNote(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }

     fun getAllNote(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    suspend fun searchByTitle(searchQuery: String): List<NoteEntity> {
        return noteDao.searchNotes(searchQuery)
    }

    suspend fun getNoteById(id: Int): NoteEntity? {
        return noteDao.getNoteById(id)
    }


}