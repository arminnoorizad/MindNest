package ir.armin.mindnest.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttachmentRepository @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val audioRecorder: AudioRecorder
) {

    fun copyUriToFile(uri: Uri): String? {
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val destinationFile = File(context.filesDir, fileName)

        return try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(destinationFile).use { output ->
                    input.copyTo(output)
                }
            }
            destinationFile.absolutePath
        } catch (e: Exception) {
            Log.e(TAG, "Error copying file", e)
            null
        }
    }

    fun deleteFile(path: String?): Boolean {
        if (path == null) return false
        return File(path).delete()
    }

    fun startAudioRecording() = audioRecorder.startRecording()

    fun stopAudioRecording() = audioRecorder.stopRecording()

    private companion object {
        const val TAG = "AttachmentRepository"
    }
}
