package com.arogyanidhi.app.models

/**
 * User information stored locally / in Firebase.
 */
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val isGuest: Boolean = false
)
