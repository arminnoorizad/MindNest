package ir.armin.mindnest.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.armin.mindnest.data.local.database.DatabasePassphraseManager
import ir.armin.mindnest.data.local.database.NoteDatabase
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        passphraseManager: DatabasePassphraseManager
    ): NoteDatabase {

        passphraseManager.clearLegacyPlaintextDatabaseIfNeeded()

        val factory = SupportOpenHelperFactory(passphraseManager.getPassphrase())

        val database = Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigrationFrom(1)
            .build()

        passphraseManager.markDatabaseEncrypted()
        return database
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase) = database.noteDao()
}
