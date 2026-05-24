package ir.armin.mindnest.data.notification

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface FcmTokenLocalDataSource {
    suspend fun saveToken(token: String)
    fun getToken(): String?
}

@Singleton
class FcmTokenLocalDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : FcmTokenLocalDataSource {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override suspend fun saveToken(token: String) {
        preferences.edit { putString(KEY_FCM_TOKEN, token)
       }
    }

    override fun getToken(): String? = preferences.getString(KEY_FCM_TOKEN, null)

    private companion object {
        const val PREFS_NAME = "mindnest_fcm_prefs"
        const val KEY_FCM_TOKEN = "fcm_token"
    }
}
