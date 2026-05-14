package com.arogyanidhi.app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arogyanidhi.app.models.QuizAnswers
import com.arogyanidhi.app.models.Scheme
import com.arogyanidhi.app.repository.ArogyaRepository
import com.arogyanidhi.app.utils.QuestionRepository
import kotlinx.coroutines.launch

class QuizViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = ArogyaRepository(app)

    private val _step = MutableLiveData(0)
    val step: LiveData<Int> = _step

    private val _answers = MutableLiveData(QuizAnswers())
    val answers: LiveData<QuizAnswers> = _answers

    private val _eligibleResults = MutableLiveData<List<Scheme>>(emptyList())
    val eligibleResults: LiveData<List<Scheme>> = _eligibleResults

    val totalQuestions: Int get() = QuestionRepository.questions.size

    fun currentQuestion() = QuestionRepository.questions[_step.value ?: 0]

    fun selectAnswer(value: String) {
        val q = currentQuestion()
        _answers.value = _answers.value?.set(q.id, value)
    }

    fun isCurrentAnswered(): Boolean {
        val q = currentQuestion()
        return _answers.value?.get(q.id) != null
    }

    fun next(): Boolean {
        val cur = _step.value ?: 0
        return if (cur < totalQuestions - 1) {
            _step.value = cur + 1
            true
        } else {
            // Done — compute results
            val ans = _answers.value ?: QuizAnswers()
            _eligibleResults.value = repo.computeEligible(ans)
            false
        }
    }

    fun prev() {
        val cur = _step.value ?: 0
        if (cur > 0) _step.value = cur - 1
    }

    fun reset() {
        _step.value = 0
        _answers.value = QuizAnswers()
        _eligibleResults.value = emptyList()
    }

    fun saveCurrentResult(onDone: (Long) -> Unit) {
        val ans = _answers.value ?: QuizAnswers()
        val results = _eligibleResults.value ?: emptyList()
        viewModelScope.launch {
            val id = repo.saveResult(ans, results)
            onDone(id)
        }
    }
}
