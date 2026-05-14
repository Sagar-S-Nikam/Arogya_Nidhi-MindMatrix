package com.arogyanidhi.app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arogyanidhi.app.repository.ArogyaRepository
import kotlinx.coroutines.launch

class DocumentsViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ArogyaRepository(app)

    private val _checkedDocs = MutableLiveData<Set<Int>>(emptySet())
    val checkedDocs: LiveData<Set<Int>> = _checkedDocs

    fun loadForScheme(schemeId: String) {
        viewModelScope.launch {
            _checkedDocs.value = repo.getCheckedDocsFor(schemeId)
        }
    }

    fun toggle(schemeId: String, docIndex: Int) {
        val cur = _checkedDocs.value ?: emptySet()
        val newSet = if (cur.contains(docIndex)) cur - docIndex else cur + docIndex
        _checkedDocs.value = newSet
        viewModelScope.launch {
            repo.setDocChecked(schemeId, docIndex, newSet.contains(docIndex))
        }
    }
}
