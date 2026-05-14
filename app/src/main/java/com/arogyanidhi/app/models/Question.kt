package com.arogyanidhi.app.models

/**
 * A single quiz question.
 */
data class Question(
    val id: String,
    val text: String,
    val options: List<Option>
)

data class Option(
    val value: String,
    val label: String
)

/**
 * The user's full set of quiz answers.
 * Keys are question IDs from QuestionRepository.
 */
data class QuizAnswers(
    val income: String? = null,        // b1, b2, b3
    val bpl: String? = null,           // yes, no
    val occupation: String? = null,    // farmer, daily, org, other
    val familySize: String? = null,    // 1, 2, 3
    val senior: String? = null,        // yes, no
    val disability: String? = null,    // yes, no
    val children: String? = null,      // yes, no
    val pregnant: String? = null,      // yes, no
    val area: String? = null           // rural, urban
) {
    fun get(key: String): String? = when (key) {
        "income" -> income
        "bpl" -> bpl
        "occupation" -> occupation
        "familySize" -> familySize
        "senior" -> senior
        "disability" -> disability
        "children" -> children
        "pregnant" -> pregnant
        "area" -> area
        else -> null
    }

    fun set(key: String, value: String): QuizAnswers = when (key) {
        "income" -> copy(income = value)
        "bpl" -> copy(bpl = value)
        "occupation" -> copy(occupation = value)
        "familySize" -> copy(familySize = value)
        "senior" -> copy(senior = value)
        "disability" -> copy(disability = value)
        "children" -> copy(children = value)
        "pregnant" -> copy(pregnant = value)
        "area" -> copy(area = value)
        else -> this
    }
}
