package com.arogyanidhi.app.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Wraps SharedPreferences for session, onboarding, and theme.
 */
class PrefsManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Onboarding
    fun isOnboardingDone(): Boolean = prefs.getBoolean(KEY_ONB_DONE, false)
    fun setOnboardingDone() = prefs.edit().putBoolean(KEY_ONB_DONE, true).apply()

    // Auth session
    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED_IN, false)
    fun setLoggedIn(b: Boolean) = prefs.edit().putBoolean(KEY_LOGGED_IN, b).apply()

    fun isGuest(): Boolean = prefs.getBoolean(KEY_GUEST, false)
    fun setGuest(b: Boolean) = prefs.edit().putBoolean(KEY_GUEST, b).apply()

    // User info
    fun setUser(name: String, email: String, phone: String) {
        prefs.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .putString(KEY_PHONE, phone)
            .apply()
    }

    fun getName(): String = prefs.getString(KEY_NAME, "") ?: ""
    fun getEmail(): String = prefs.getString(KEY_EMAIL, "") ?: ""
    fun getPhone(): String = prefs.getString(KEY_PHONE, "") ?: ""

    // Dark mode
    fun isDarkMode(): Boolean = prefs.getBoolean(KEY_DARK, false)
    fun setDarkMode(b: Boolean) = prefs.edit().putBoolean(KEY_DARK, b).apply()

    fun logout() {
        prefs.edit()
            .remove(KEY_LOGGED_IN)
            .remove(KEY_GUEST)
            .remove(KEY_NAME)
            .remove(KEY_EMAIL)
            .remove(KEY_PHONE)
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "arogya_prefs"
        private const val KEY_ONB_DONE = "onb_done"
        private const val KEY_LOGGED_IN = "logged_in"
        private const val KEY_GUEST = "guest"
        private const val KEY_NAME = "user_name"
        private const val KEY_EMAIL = "user_email"
        private const val KEY_PHONE = "user_phone"
        private const val KEY_DARK = "dark_mode"
    }
}
