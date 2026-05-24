package ir.armin.mindnest.features.ui.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.EntryPointAccessors
import ir.armin.mindnest.di.AnalyticsEntryPoint

@Composable
fun TrackScreenView(screenName: String) {
    val context = LocalContext.current
    LaunchedEffect(screenName) {
        val analytics = EntryPointAccessors.fromApplication(
            context.applicationContext,
            AnalyticsEntryPoint::class.java
        ).analyticsRepository()
        analytics.logScreen(screenName)
    }
}
