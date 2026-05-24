package ir.armin.mindnest.data.repository

interface PushNotificationRepository {
    fun ensureNotificationChannel()
    suspend fun onNewFcmToken(token: String)
    fun showNotification(
        title: String,
        body: String,
        data: Map<String, String> = emptyMap()
    )
}
