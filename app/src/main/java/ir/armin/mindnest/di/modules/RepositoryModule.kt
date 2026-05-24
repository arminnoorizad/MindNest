package ir.armin.mindnest.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.armin.mindnest.data.notification.FcmTokenLocalDataSource
import ir.armin.mindnest.data.notification.FcmTokenLocalDataSourceImpl
import ir.armin.mindnest.data.repository.AndroidAudioRecorder
import ir.armin.mindnest.data.repository.AudioRecorder
import ir.armin.mindnest.data.repository.NoteRepository
import ir.armin.mindnest.data.repository.NoteRepositoryImpl
import ir.armin.mindnest.data.repository.PushNotificationRepository
import ir.armin.mindnest.data.repository.PushNotificationRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository

    @Binds
    @Singleton
    abstract fun bindAudioRecorder(
        androidAudioRecorder: AndroidAudioRecorder
    ): AudioRecorder

    @Binds
    @Singleton
    abstract fun bindPushNotificationRepository(
        pushNotificationRepositoryImpl: PushNotificationRepositoryImpl
    ): PushNotificationRepository

    @Binds
    @Singleton
    abstract fun bindFcmTokenLocalDataSource(
        fcmTokenLocalDataSourceImpl: FcmTokenLocalDataSourceImpl
    ): FcmTokenLocalDataSource
}
