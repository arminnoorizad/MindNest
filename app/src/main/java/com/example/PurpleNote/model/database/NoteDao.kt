    package com.example.PurpleNote.model.database

    import androidx.room.Dao
    import androidx.room.Delete
    import androidx.room.Query
    import androidx.room.Upsert

    @Dao
    interface NoteDao {

        @Upsert
        suspend fun upsertNote(noteEntity: NoteEntity)


        @Delete
        suspend fun deleteNote(noteEntity: NoteEntity)




        @Query("SELECT * FROM ${DBHandler.TABLE_NAME} ORDER BY date DESC") // ترتیب نتایج
        fun getAllNotes(): kotlinx.coroutines.flow.Flow<List<NoteEntity>>

        @Query("SELECT * FROM ${DBHandler.TABLE_NAME} WHERE title LIKE :searchQuery OR description LIKE :searchQuery ")
        suspend fun searchNotes(searchQuery: String): List<NoteEntity>


        @Query("SELECT * FROM ${DBHandler.TABLE_NAME} WHERE id = :id")
        suspend fun getNoteById(id: Int): NoteEntity?






    }