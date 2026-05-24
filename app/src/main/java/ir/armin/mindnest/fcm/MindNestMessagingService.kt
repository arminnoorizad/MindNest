package ir.armin.mindnest.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import ir.armin.mindnest.domain.usecase.ProcessFcmMessageUseCase
import ir.armin.mindnest.data.repository.PushNotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MindNestMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var pushNotificationRepository: PushNotificationRepository

    @Inject
    lateinit var processFcmMessageUseCase: ProcessFcmMessageUseCase

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        pushNotificationRepository.ensureNotificationChannel()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        serviceScope.launch {
            pushNotificationRepository.onNewFcmToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        serviceScope.launch {
            try {
                processFcmMessageUseCase(message)
            } catch (_: Exception) {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
