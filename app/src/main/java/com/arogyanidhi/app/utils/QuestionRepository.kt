package com.arogyanidhi.app.utils

import com.arogyanidhi.app.models.Option
import com.arogyanidhi.app.models.Question

/**
 * Hard-coded 9 questions for the eligibility quiz.
 */
object QuestionRepository {

    val questions: List<Question> = listOf(
        Question(
            id = "income",
            text = "What is your annual family income?",
            options = listOf(
                Option("b1", "Below ₹1,20,000"),
                Option("b2", "₹1,20,000 — ₹3,00,000"),
                Option("b3", "Above ₹3,00,000")
            )
        ),
        Question(
            id = "bpl",
            text = "Do you have a BPL ration card?",
            options = listOf(
                Option("yes", "Yes"),
                Option("no", "No")
            )
        ),
        Question(
            id = "occupation",
            text = "What is the primary occupation in your family?",
            options = listOf(
                Option("farmer", "Farmer / Agriculture worker"),
                Option("daily", "Daily wage labourer"),
                Option("org", "Salaried (organized sector)"),
                Option("other", "Self-employed / Other")
            )
        ),
        Question(
            id = "familySize",
            text = "How many people in your family?",
            options = listOf(
                Option("1", "1 — 2 people"),
                Option("2", "3 — 5 people"),
                Option("3", "6 or more people")
            )
        ),
        Question(
            id = "senior",
            text = "Any senior citizen (60+) in family?",
            options = listOf(
                Option("yes", "Yes"),
                Option("no", "No")
            )
        ),
        Question(
            id = "disability",
            text = "Anyone with disability in family?",
            options = listOf(
                Option("yes", "Yes"),
                Option("no", "No")
            )
        ),
        Question(
            id = "children",
            text = "Any children under 18 in family?",
            options = listOf(
                Option("yes", "Yes"),
                Option("no", "No")
            )
        ),
        Question(
            id = "pregnant",
            text = "Is anyone in the family currently pregnant?",
            options = listOf(
                Option("yes", "Yes"),
                Option("no", "No")
            )
        ),
        Question(
            id = "area",
            text = "Where do you live?",
            options = listOf(
                Option("rural", "Rural area"),
                Option("urban", "Urban area")
            )
        )
    )
}
