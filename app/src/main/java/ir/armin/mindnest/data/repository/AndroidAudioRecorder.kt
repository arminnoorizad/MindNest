package ir.armin.mindnest.data.repository

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AndroidAudioRecorder @Inject constructor(
    @param:ApplicationContext private val context: Context
) : AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var currentOutputFile: File? = null

    @Suppress("DEPRECATION")
    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
    }

    override fun startRecording(): String {
        // Use internal files directory for permanent storage
        val voiceDir = File(context.filesDir, "voices").apply { if (!exists()) mkdirs() }
        val fileName = "voice_${System.currentTimeMillis()}.m4a"
        currentOutputFile = File(voiceDir, fileName)

        try {
            recorder = createRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(currentOutputFile!!.absolutePath)
                prepare()
                start()
            }
            Log.d(TAG, "Recording started at: ${currentOutputFile!!.absolutePath}")
            return currentOutputFile!!.absolutePath
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start recording", e)
            releaseRecorder()
            throw e
        }
    }

    override fun stopRecording(): String? {
        return try {
            recorder?.apply {
                stop()
                reset()
                release()
            }
            val path = currentOutputFile?.absolutePath
            Log.d(TAG, "Recording stopped. File: $path")
            recorder = null
            currentOutputFile = null
            path
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping recorder", e)
            releaseRecorder()
            null
        }
    }

    private fun releaseRecorder() {
        try {
            recorder?.release()
        } catch (_: Exception) {}
        recorder = null
        currentOutputFile = null
    }

    override fun isRecording(): Boolean = recorder != null

    private companion object {
        const val TAG = "AndroidAudioRecorder"
    }
}
