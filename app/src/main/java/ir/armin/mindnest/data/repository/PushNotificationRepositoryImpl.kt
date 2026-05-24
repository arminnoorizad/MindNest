package ir.armin.mindnest.data.repository

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.armin.mindnest.R
import ir.armin.mindnest.data.notification.FcmTokenLocalDataSource
import ir.armin.mindnest.data.notification.NotificationConstants
import ir.armin.mindnest.features.ui.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushNotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fcmTokenLocalDataSource: FcmTokenLocalDataSource
) : PushNotificationRepository {

    private val notificationManager: NotificationManager
        get() = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun ensureNotificationChannel() {
        val channel = android.app.NotificationChannel(
            NotificationConstants.CHANNEL_ID,
            NotificationConstants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = NotificationConstants.CHANNEL_DESCRIPTION
        }
        notificationManager.createNotificationChannel(channel)
    }

    override suspend fun onNewFcmToken(token: String) {
        fcmTokenLocalDataSource.saveToken(token)
        Log.d("MyFCMService", "New token: $token")
        Log.i(TAG, "FCM token refreshed and persisted locally." )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun showNotification(
        title: String,
        body: String,
        data: Map<String, String>
    ) {
        ensureNotificationChannel()

        val destination = data[NotificationConstants.EXTRA_DESTINATION]
            ?: NotificationConstants.DESTINATION_NOTE_LIST

        val contentIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(NotificationConstants.EXTRA_DESTINATION, destination)
            data.forEach { (key, value) -> putExtra(key, value) }
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CODE_OPEN_NOTE_LIST,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(NOTIFICATION_ID_INCOMING_MESSAGE, notification)
    }

    private companion object {
        const val TAG = "PushNotificationRepo"
        const val NOTIFICATION_ID_INCOMING_MESSAGE = 1001
        const val REQUEST_CODE_OPEN_NOTE_LIST = 2001
    }
}
