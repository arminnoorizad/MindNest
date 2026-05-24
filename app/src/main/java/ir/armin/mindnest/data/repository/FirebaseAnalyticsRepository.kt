package ir.armin.mindnest.data.repository

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.armin.mindnest.data.analytics.AnalyticsEvents
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsRepository @Inject constructor(
    @ApplicationContext context: Context
) : AnalyticsRepository {

    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun logScreen(screenName: String) {
        logEvent(
            AnalyticsEvents.SCREEN_VIEW,
            mapOf(AnalyticsEvents.PARAM_SCREEN_NAME to screenName)
        )
    }

    override fun logEvent(eventName: String, params: Map<String, Any>) {
        runCatching {
            firebaseAnalytics.logEvent(eventName, params.toAnalyticsBundle())
        }.onFailure { error ->
            Log.w(TAG, "Failed to log analytics event: $eventName", error)
        }
    }

    private fun Map<String, Any>.toAnalyticsBundle(): Bundle {
        return Bundle().apply {
            forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Double -> putDouble(key, value)
                    is Float -> putFloat(key, value)
                    is Boolean -> putBoolean(key, value)
                    else -> putString(key, value.toString())
                }
            }
        }
    }

    private companion object {
        const val TAG = "FirebaseAnalytics"
    }
}
