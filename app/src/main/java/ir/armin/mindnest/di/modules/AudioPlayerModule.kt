package ir.armin.mindnest.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.armin.mindnest.data.repository.AudioPlayer
import ir.armin.mindnest.utils.AndroidAudioPlayer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AudioPlayerModule {

    @Binds
    @Singleton
    abstract fun bindAudioPlayer(
        androidAudioPlayer: AndroidAudioPlayer
    ): AudioPlayer
}
