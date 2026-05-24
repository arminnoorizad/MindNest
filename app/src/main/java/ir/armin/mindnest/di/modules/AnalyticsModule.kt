package ir.armin.mindnest.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.armin.mindnest.data.repository.AnalyticsRepository
import ir.armin.mindnest.data.repository.FirebaseAnalyticsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

    @Binds
    @Singleton
    abstract fun bindAnalyticsRepository(
        firebaseAnalyticsRepository: FirebaseAnalyticsRepository
    ): AnalyticsRepository
}
