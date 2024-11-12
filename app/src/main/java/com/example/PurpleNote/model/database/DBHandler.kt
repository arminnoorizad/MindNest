package com.example.PurpleNote.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [NoteEntity::class],
    version = DBHandler.DB_VERSION
)

abstract class DBHandler : RoomDatabase() {
    abstract fun dao(): NoteDao

    companion object {

        private const val DB_NAME = "NoteDatabase.db"
         const val TABLE_NAME = "NoteTable"
        const val DB_VERSION = 2


        private var INSTANCE: DBHandler? = null


        fun getDatabase(context: Context): DBHandler {


            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(
                    context,
                    DBHandler::class.java,
                    DBHandler.DB_NAME
                ).fallbackToDestructiveMigration().build()


            }
            return INSTANCE!!

        }


    }


}