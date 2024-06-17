package com.bangkit.eyetify.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(result: ResultEntity)

    @Query("SELECT * FROM results")
    fun getAllResults(): LiveData<List<ResultEntity>>

    @Query("DELETE FROM results WHERE id = :id")
    suspend fun deleteResultById(id: Int)
}