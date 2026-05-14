package com.arogyanidhi.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arogyanidhi.app.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repo = AuthRepository()

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _success = MutableLiveData<String?>(null) // uid on success
    val success: LiveData<String?> = _success

    fun signUp(email: String, password: String) {
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            val r = repo.signUp(email, password)
            _loading.value = false
            r.onSuccess { _success.value = it }
                .onFailure { _error.value = it.message ?: "Sign-up failed" }
        }
    }

    fun signIn(email: String, password: String) {
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            val r = repo.signIn(email, password)
            _loading.value = false
            r.onSuccess { _success.value = it }
                .onFailure { _error.value = it.message ?: "Login failed" }
        }
    }

    fun forgotPassword(email: String) {
        _loading.value = true
        viewModelScope.launch {
            val r = repo.sendPasswordReset(email)
            _loading.value = false
            r.onSuccess { _success.value = "reset_sent" }
                .onFailure { _error.value = it.message ?: "Could not send reset email" }
        }
    }

    fun clearMessages() {
        _error.value = null
        _success.value = null
    }
}
