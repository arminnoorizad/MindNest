package ir.armin.mindnest.features.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import ir.armin.mindnest.data.analytics.AnalyticsEvents
import ir.armin.mindnest.data.notification.NotificationConstants
import ir.armin.mindnest.data.repository.AnalyticsRepository
import ir.armin.mindnest.data.repository.PushNotificationRepository
import ir.armin.mindnest.features.theme.MindNestTheme
import ir.armin.mindnest.navigation.AppNavigation
import ir.armin.mindnest.navigation.Destination
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var pushNotificationRepository: PushNotificationRepository

    @Inject
    lateinit var analyticsRepository: AnalyticsRepository

    private var deepLinkDestination by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pushNotificationRepository.ensureNotificationChannel()

        val startDestination = resolveStartDestination(intent)
        if (startDestination == Destination.NoteListScreen.route) {
            analyticsRepository.logEvent(AnalyticsEvents.NOTIFICATION_OPENED)
        }
        deepLinkDestination = extractDeepLinkDestination(intent)

        setContent {
            MindNestTheme(dynamicColor = false , darkTheme = false
            ) {
                NotificationPermissionHandler()
                AppNavigation(
                    startDestination = startDestination,
                    deepLinkDestination = deepLinkDestination
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        deepLinkDestination = extractDeepLinkDestination(intent)
        if (deepLinkDestination == Destination.NoteListScreen.route) {
            analyticsRepository.logEvent(AnalyticsEvents.NOTIFICATION_OPENED)
        }
    }

    private fun resolveStartDestination(intent: Intent?): String {
        return extractDeepLinkDestination(intent) ?: Destination.SplashScreen.route
    }

    private fun extractDeepLinkDestination(intent: Intent?): String? {
        val destination = intent?.getStringExtra(NotificationConstants.EXTRA_DESTINATION)
        return if (destination == NotificationConstants.DESTINATION_NOTE_LIST) {
            Destination.NoteListScreen.route
        } else {
            null
        }
    }
}
