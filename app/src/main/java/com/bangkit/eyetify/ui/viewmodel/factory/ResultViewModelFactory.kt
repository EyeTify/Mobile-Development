package com.bangkit.eyetify.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.eyetify.data.injection.ResultInjection
import com.bangkit.eyetify.data.repository.ResultRepository
import com.bangkit.eyetify.ui.viewmodel.model.HistoryViewModel

class ResultViewModelFactory(private val resultRepository: ResultRepository)
    : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(resultRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ResultViewModelFactory? = null
        fun getInstance(context: Context): ResultViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ResultViewModelFactory(
                    ResultInjection.provideResultRepository(context)
                )
            }.also { instance = it }
    }
}
