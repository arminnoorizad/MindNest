package ir.armin.mindnest.data.repository


interface AudioRecorder {
    fun startRecording(): String
    fun stopRecording(): String?
    fun isRecording(): Boolean
}