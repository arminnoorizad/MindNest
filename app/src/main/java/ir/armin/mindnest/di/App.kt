package ir.armin.mindnest.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
	override fun onCreate() {
		super.onCreate()
		try {
			System.loadLibrary("sqlcipher")
		} catch (t: Throwable) {
	}
	}
}
