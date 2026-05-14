package com.arogyanidhi.app.models

/**
 * Government health scheme.
 */
data class Scheme(
    val id: String,
    val name: String,
    val category: String,
    val benefit: String,
    val description: String,
    val eligibility: String,
    val whyQualify: String,
    val requiredDocs: List<String>
)
