package com.arogyanidhi.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DocumentProgressDao {

    @Query("SELECT * FROM document_progress WHERE schemeId = :schemeId")
    suspend fun getForScheme(schemeId: String): List<DocumentProgressEntity>

    @Query("SELECT compositeKey FROM document_progress WHERE isChecked = 1")
    suspend fun getCheckedKeys(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: DocumentProgressEntity)

    @Query("DELETE FROM document_progress")
    suspend fun clearAll()
}
