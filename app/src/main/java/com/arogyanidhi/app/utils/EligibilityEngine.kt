package com.arogyanidhi.app.utils

import com.arogyanidhi.app.models.QuizAnswers
import com.arogyanidhi.app.models.Scheme

/**
 * Decision-tree logic that decides which schemes a family qualifies for.
 * Each scheme has its own rule expressed as a simple if/else against QuizAnswers.
 *
 * To add a new scheme: append a new branch in `isEligible()`.
 */
object EligibilityEngine {

    /**
     * Returns the list of schemes the user is eligible for.
     */
    fun evaluate(answers: QuizAnswers): List<Scheme> {
        return SchemeRepository.schemes.filter { scheme ->
            isEligible(scheme.id, answers)
        }
    }

    private fun isEligible(schemeId: String, a: QuizAnswers): Boolean = when (schemeId) {

        // Ayushman Bharat: BPL or low/mid income
        "ayushman" ->
            a.bpl == "yes" || a.income == "b1" || a.income == "b2"

        // Arogya Karnataka: all Karnataka residents
        "arogya_k" -> true

        // Janani Suraksha: pregnant + (BPL or below-1L income)
        "janani" ->
            a.pregnant == "yes" && (a.bpl == "yes" || a.income == "b1")

        // RBSK: family has children
        "rbsk" ->
            a.children == "yes"

        // ESI: organized-sector + below ₹3L income
        "esi" ->
            a.occupation == "org" && (a.income == "b1" || a.income == "b2")

        // Vajpayee Arogyashree: BPL only
        "vajpayee" ->
            a.bpl == "yes"

        // NSAP Disability Pension: has disability + (BPL or low income)
        "nspd" ->
            a.disability == "yes" && (a.bpl == "yes" || a.income == "b1")

        // Old Age Pension: senior + (BPL or low income)
        "oldage" ->
            a.senior == "yes" && (a.bpl == "yes" || a.income == "b1")

        else -> false
    }
}
