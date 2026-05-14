package com.arogyanidhi.app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedResultDao {

    @Query("SELECT * FROM saved_results ORDER BY date DESC")
    fun observeAll(): LiveData<List<SavedResultEntity>>

    @Query("SELECT * FROM saved_results ORDER BY date DESC")
    suspend fun getAll(): List<SavedResultEntity>

    @Query("SELECT * FROM saved_results WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): SavedResultEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: SavedResultEntity): Long

    @Query("DELETE FROM saved_results WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM saved_results")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM saved_results")
    suspend fun count(): Int
}
