package com.arogyanidhi.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.arogyanidhi.app.utils.PrefsManager

/**
 * Application class — initializes app-level dependencies (Prefs, Theme).
 */
class ArogyaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Apply saved theme (light/dark)
        val isDark = PrefsManager(this).isDarkMode()
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    companion object {
        lateinit var instance: ArogyaApp
            private set
    }
}
