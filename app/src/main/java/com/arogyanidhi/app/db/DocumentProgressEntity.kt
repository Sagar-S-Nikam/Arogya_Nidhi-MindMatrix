package com.arogyanidhi.app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tracks which documents a user has marked as ready.
 * compositeKey = "schemeId_docIndex"
 */
@Entity(tableName = "document_progress")
data class DocumentProgressEntity(
    @PrimaryKey val compositeKey: String,
    val schemeId: String,
    val docIndex: Int,
    val isChecked: Boolean,
    val updatedAt: Long
)
