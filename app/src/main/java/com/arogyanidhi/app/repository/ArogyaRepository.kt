package com.arogyanidhi.app.repository

import android.content.Context
import com.arogyanidhi.app.db.AppDatabase
import com.arogyanidhi.app.db.DocumentProgressEntity
import com.arogyanidhi.app.db.SavedResultEntity
import com.arogyanidhi.app.models.QuizAnswers
import com.arogyanidhi.app.models.Scheme
import com.arogyanidhi.app.utils.EligibilityEngine

/**
 * Repository — single source of truth for the rest of the app.
 */
class ArogyaRepository(context: Context) {

    private val db = AppDatabase.get(context)
    private val savedDao = db.savedResultDao()
    private val docDao = db.documentProgressDao()

    // --- Eligibility ---
    fun computeEligible(answers: QuizAnswers): List<Scheme> =
        EligibilityEngine.evaluate(answers)

    // --- Saved results ---
    suspend fun saveResult(answers: QuizAnswers, eligible: List<Scheme>): Long {
        val entity = SavedResultEntity(
            date = System.currentTimeMillis(),
            schemeCount = eligible.size,
            eligibleSchemeIds = eligible.joinToString(",") { it.id },
            eligibleSchemeNames = eligible.joinToString(",") { it.name },
            incomeBracket = answers.income,
            bpl = answers.bpl,
            occupation = answers.occupation
        )
        return savedDao.insert(entity)
    }

    fun observeSavedResults() = savedDao.observeAll()
    suspend fun getSavedResults() = savedDao.getAll()
    suspend fun deleteSaved(id: Long) = savedDao.deleteById(id)
    suspend fun clearSaved() = savedDao.clearAll()

    // --- Document progress ---
    suspend fun setDocChecked(schemeId: String, docIndex: Int, checked: Boolean) {
        docDao.upsert(
            DocumentProgressEntity(
                compositeKey = "${schemeId}_$docIndex",
                schemeId = schemeId,
                docIndex = docIndex,
                isChecked = checked,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun getCheckedDocsFor(schemeId: String): Set<Int> =
        docDao.getForScheme(schemeId).filter { it.isChecked }.map { it.docIndex }.toSet()
}
