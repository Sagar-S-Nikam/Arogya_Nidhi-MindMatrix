package com.arogyanidhi.app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Persisted eligibility result.
 * eligibleSchemeIds is a comma-separated list of scheme IDs.
 */
@Entity(tableName = "saved_results")
data class SavedResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Long,
    val schemeCount: Int,
    val eligibleSchemeIds: String,
    val eligibleSchemeNames: String,
    val incomeBracket: String?,
    val bpl: String?,
    val occupation: String?
)
