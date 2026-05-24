package ir.armin.mindnest.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.armin.mindnest.data.repository.AudioPlayer
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidAudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioPlayer {

    private var player: MediaPlayer? = null
    private var onCompletionListener: (() -> Unit)? = null

    override fun setOnCompletionListener(listener: () -> Unit) {
        onCompletionListener = listener
    }

    override fun isPlaying(): Boolean = try {
        player?.isPlaying == true
    } catch (e: Exception) {
        false
    }

    override fun getCurrentPositionMs(): Int = try {
        player?.currentPosition ?: 0
    } catch (e: Exception) {
        0
    }

    override fun getDurationMs(): Int = try {
        player?.duration?.coerceAtLeast(0) ?: 0
    } catch (e: Exception) {
        0
    }

    override fun startPlayback(path: String): Boolean {
        if (path.isBlank()) return false
        val file = File(path)
        if (!file.exists()) {
            Log.e(TAG, "Audio file not found: $path")
            return false
        }

        stopPlayback()

        return try {
            player = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                // Using Uri via Context is more reliable for internal storage
                setDataSource(context, Uri.fromFile(file))
                
                setOnCompletionListener {
                    Log.d(TAG, "Playback completed naturally")
                    onCompletionListener?.invoke()
                }
                
                setOnErrorListener { _, what, extra ->
                    Log.e(TAG, "MediaPlayer error: $what, $extra")
                    stopPlayback()
                    onCompletionListener?.invoke()
                    true
                }

                prepare()
                start()
            }
            Log.d(TAG, "Playback started successfully")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start playback", e)
            stopPlayback()
            false
        }
    }

    override fun stopPlayback() {
        try {
            player?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.reset()
                it.release()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping player", e)
        } finally {
            player = null
        }
    }

    private companion object {
        const val TAG = "AndroidAudioPlayer"
    }
}
