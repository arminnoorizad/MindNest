package ir.armin.mindnest.data.repository

interface AnalyticsRepository {
    fun logScreen(screenName: String)
    fun logEvent(eventName: String, params: Map<String, Any> = emptyMap())
}
