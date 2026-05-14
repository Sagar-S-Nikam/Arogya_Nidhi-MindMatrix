package com.arogyanidhi.app.utils

import android.util.Patterns

/**
 * Simple input validators for auth forms.
 */
object Validator {

    fun isValidEmail(input: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(input.trim()).matches()

    fun isValidPhone(input: String): Boolean {
        val clean = input.replace("\\s+".toRegex(), "").replace("+", "")
        return clean.matches(Regex("\\d{10,13}"))
    }

    fun isValidPassword(input: String): Boolean = input.length >= 6

    fun isEmailOrPhone(input: String): Boolean =
        isValidEmail(input) || isValidPhone(input)
}
