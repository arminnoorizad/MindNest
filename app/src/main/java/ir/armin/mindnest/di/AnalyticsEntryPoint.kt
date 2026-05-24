package ir.armin.mindnest.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.armin.mindnest.data.repository.AnalyticsRepository

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AnalyticsEntryPoint {
    fun analyticsRepository(): AnalyticsRepository
}
