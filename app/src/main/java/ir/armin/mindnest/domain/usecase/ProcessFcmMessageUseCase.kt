package ir.armin.mindnest.domain.usecase

import com.google.firebase.messaging.RemoteMessage
import ir.armin.mindnest.data.notification.NotificationConstants
import ir.armin.mindnest.data.repository.PushNotificationRepository
import javax.inject.Inject

class ProcessFcmMessageUseCase @Inject constructor(
    private val pushNotificationRepository: PushNotificationRepository
) {

    operator fun invoke(message: RemoteMessage) {
        val title = message.notification?.title
            ?: message.data[NotificationConstants.DATA_KEY_TITLE]
            ?: DEFAULT_TITLE

        val body = message.notification?.body
            ?: message.data[NotificationConstants.DATA_KEY_BODY]
            ?: DEFAULT_BODY

        pushNotificationRepository.showNotification(
            title = title,
            body = body,
            data = message.data
        )
    }

    private companion object {
        const val DEFAULT_TITLE = "Mind Nest"
        const val DEFAULT_BODY = "You have a new update."
    }
}
