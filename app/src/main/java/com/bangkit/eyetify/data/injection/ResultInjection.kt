package com.bangkit.eyetify.data.injection

import android.content.Context
import com.bangkit.eyetify.data.repository.ResultRepository
import com.bangkit.eyetify.data.room.AppDatabase

object ResultInjection {
    fun provideResultRepository(context: Context): ResultRepository {
        val database = AppDatabase.getInstance(context)
        val dao = database.resultDao()
        return ResultRepository.getInstance(dao)
    }
}