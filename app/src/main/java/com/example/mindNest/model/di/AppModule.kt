package com.example.mindNest.model.di

import android.content.Context
import com.example.mindNest.model.database.DBHandler
import com.example.mindNest.model.database.NoteDao
import com.example.mindNest.model.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DBHandler {
        return DBHandler.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideNoteDao(dbHandler: DBHandler): NoteDao {
        return dbHandler.dao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }
}
