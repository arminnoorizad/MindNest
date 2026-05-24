package ir.armin.mindnest.data.local.database

import android.content.Context
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.FileInputStream
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class DatabasePassphraseManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val encryptedPrefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getPassphrase(): ByteArray {
        val encoded = encryptedPrefs.getString(KEY_PASSPHRASE, null)
        if (encoded != null) {
            return Base64.decode(encoded, Base64.NO_WRAP)
        }

        val passphrase = ByteArray(PASSPHRASE_LENGTH).also { bytes ->
            SecureRandom().nextBytes(bytes)
        }
        encryptedPrefs.edit {
            putString(KEY_PASSPHRASE, Base64.encodeToString(passphrase, Base64.NO_WRAP))
        }
        return passphrase
    }


    fun clearLegacyPlaintextDatabaseIfNeeded() {
        if (encryptedPrefs.getBoolean(KEY_DB_ENCRYPTED, false)) return

        val databaseFile = context.getDatabasePath(NoteDatabase.DATABASE_NAME)
        if (!databaseFile.exists()) return

        val isPlaintext = FileInputStream(databaseFile).use { input ->
            val header = ByteArray(PLAIN_SQLITE_HEADER.length)
            val read = input.read(header)
            read >= PLAIN_SQLITE_HEADER.length &&
                String(header, Charsets.US_ASCII) == PLAIN_SQLITE_HEADER
        }

        if (isPlaintext) {
            context.deleteDatabase(NoteDatabase.DATABASE_NAME)
        }
    }

    fun markDatabaseEncrypted() {
        encryptedPrefs.edit { putBoolean(KEY_DB_ENCRYPTED, true) }
    }

    private companion object {
        const val PREFS_FILE_NAME = "mindnest_db_secure_prefs"
        const val KEY_PASSPHRASE = "db_passphrase"
        const val KEY_DB_ENCRYPTED = "db_encrypted_v2"
        const val PASSPHRASE_LENGTH = 32
        const val PLAIN_SQLITE_HEADER = "SQLite format 3"
    }
}
