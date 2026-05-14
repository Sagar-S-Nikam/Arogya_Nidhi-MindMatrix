package com.arogyanidhi.app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arogyanidhi.app.repository.ArogyaRepository
import kotlinx.coroutines.launch

class SavedResultsViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ArogyaRepository(app)

    val savedResults = repo.observeSavedResults()

    fun delete(id: Long) {
        viewModelScope.launch { repo.deleteSaved(id) }
    }

    fun clearAll() {
        viewModelScope.launch { repo.clearSaved() }
    }
}
