package com.bangkit.eyetify.data.repository

import androidx.lifecycle.LiveData
import com.bangkit.eyetify.data.room.ResultDao
import com.bangkit.eyetify.data.room.ResultEntity

class ResultRepository private constructor(
    private val resultDao: ResultDao
){
    fun getAllResultSaved() : LiveData<List<ResultEntity>>{
        return resultDao.getAllResults()
    }

    suspend fun insertResultSaved(resultEntity: ResultEntity){
        return resultDao.insert(resultEntity)
    }

    suspend fun deleteResultById(id: Int) {
        resultDao.deleteResultById(id)
    }

    companion object {
        @Volatile
        private var instance: ResultRepository? = null
        fun getInstance(
            cancerSavedDao: ResultDao,
        ): ResultRepository = instance ?: synchronized(this) {
            instance ?: ResultRepository(cancerSavedDao).also {
                instance = it
            }
        }
    }
}