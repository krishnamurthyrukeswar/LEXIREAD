package com.lexiread.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * LexiRead Application class.
 * Annotated with @HiltAndroidApp to trigger Hilt's code generation
 * and serve as the application-level dependency container.
 */
@HiltAndroidApp
class LexiReadApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Future initialization:
        // - Firebase
        // - Timber (logging)
        // - WorkManager (sync)
    }
}
