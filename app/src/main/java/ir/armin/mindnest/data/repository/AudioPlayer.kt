package ir.armin.mindnest.data.repository

interface AudioPlayer {
    fun startPlayback(path: String): Boolean
    fun stopPlayback()
    fun isPlaying(): Boolean
    fun getCurrentPositionMs(): Int
    fun getDurationMs(): Int
    fun setOnCompletionListener(listener: () -> Unit)
}
