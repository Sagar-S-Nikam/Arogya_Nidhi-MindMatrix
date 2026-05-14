package com.arogyanidhi.app.utils

import com.arogyanidhi.app.models.Scheme

/**
 * Static catalog of all government health schemes.
 * Could be loaded from assets/JSON or remote in a later version.
 */
object SchemeRepository {

    val schemes: List<Scheme> = listOf(
        Scheme(
            id = "ayushman",
            name = "Ayushman Bharat - PM-JAY",
            category = "National",
            benefit = "₹5 Lakh / year",
            description = "Free hospitalization up to ₹5,00,000 per family per year for secondary and tertiary care at empaneled hospitals.",
            eligibility = "BPL families & SECC-listed households",
            whyQualify = "Your BPL status / low income qualifies you",
            requiredDocs = listOf(
                "Aadhaar Card",
                "Ration Card",
                "SECC database name",
                "Mobile number",
                "Family photo"
            )
        ),
        Scheme(
            id = "arogya_k",
            name = "Arogya Karnataka",
            category = "State",
            benefit = "Free state-wide",
            description = "State scheme covering general and complex treatments at government and empaneled private hospitals across Karnataka.",
            eligibility = "All Karnataka residents",
            whyQualify = "Available to all Karnataka residents",
            requiredDocs = listOf(
                "Aadhaar Card",
                "BPL/APL Ration Card",
                "Karnataka residence proof"
            )
        ),
        Scheme(
            id = "janani",
            name = "Janani Suraksha Yojana",
            category = "Maternal",
            benefit = "Cash + free delivery",
            description = "Cash assistance and free maternal care for pregnant women below poverty line.",
            eligibility = "Pregnant women, BPL families",
            whyQualify = "Family has a pregnant member and qualifies under BPL",
            requiredDocs = listOf(
                "Aadhaar Card",
                "BPL Card",
                "MCP card",
                "Bank passbook"
            )
        ),
        Scheme(
            id = "rbsk",
            name = "Rashtriya Bal Swasthya Karyakram",
            category = "Child",
            benefit = "Free child screening",
            description = "Free screening and treatment for children aged 0-18 for diseases, deficiencies, and disabilities.",
            eligibility = "Children aged 0-18",
            whyQualify = "Family has children under 18",
            requiredDocs = listOf(
                "Aadhaar of child",
                "Birth certificate",
                "School ID"
            )
        ),
        Scheme(
            id = "esi",
            name = "Employees State Insurance (ESI)",
            category = "Worker",
            benefit = "For salaried workers",
            description = "Health insurance for workers in organized sector earning up to ₹21,000/month.",
            eligibility = "Organized-sector workers, income ≤ ₹21k/month",
            whyQualify = "Organized-sector worker meeting income criteria",
            requiredDocs = listOf(
                "ESI card",
                "Employer certificate",
                "Aadhaar Card"
            )
        ),
        Scheme(
            id = "vajpayee",
            name = "Vajpayee Arogyashree",
            category = "Tertiary",
            benefit = "Free major surgery",
            description = "Free tertiary care surgeries (heart, kidney, cancer, neuro) for BPL families.",
            eligibility = "BPL families needing major surgery",
            whyQualify = "BPL status qualifies for tertiary care",
            requiredDocs = listOf(
                "BPL Ration Card",
                "Aadhaar Card",
                "Doctor referral",
                "Income certificate"
            )
        ),
        Scheme(
            id = "nspd",
            name = "NSAP - Disability Pension",
            category = "Disability",
            benefit = "Monthly pension",
            description = "Monthly pension and medical support for persons with severe disabilities in BPL families.",
            eligibility = "BPL families with disabled members",
            whyQualify = "Family has a member with disability",
            requiredDocs = listOf(
                "Disability certificate",
                "BPL Card",
                "Aadhaar Card",
                "Bank passbook"
            )
        ),
        Scheme(
            id = "oldage",
            name = "Indira Gandhi Old Age Pension",
            category = "Senior",
            benefit = "Monthly support",
            description = "Monthly pension for senior citizens (60+) from BPL families to support health and basic needs.",
            eligibility = "Seniors 60+ in BPL families",
            whyQualify = "Family has senior citizen 60+",
            requiredDocs = listOf(
                "Age proof",
                "BPL Card",
                "Aadhaar Card",
                "Bank passbook"
            )
        )
    )

    fun findById(id: String): Scheme? = schemes.firstOrNull { it.id == id }

    fun search(query: String, category: String): List<Scheme> {
        val q = query.trim().lowercase()
        return schemes.filter { s ->
            val matchQ = q.isEmpty() ||
                s.name.lowercase().contains(q) ||
                s.description.lowercase().contains(q)
            val matchC = category == "All" || s.category == category
            matchQ && matchC
        }
    }

    fun categories(): List<String> =
        listOf("All") + schemes.map { it.category }.distinct()
}
