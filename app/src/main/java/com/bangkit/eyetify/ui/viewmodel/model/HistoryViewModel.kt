package com.bangkit.eyetify.ui.viewmodel.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.eyetify.data.repository.ResultRepository
import com.bangkit.eyetify.data.room.ResultEntity
import kotlinx.coroutines.launch

class HistoryViewModel(private val resultRepository: ResultRepository) : ViewModel() {
    fun getAllSavedResult() = resultRepository.getAllResultSaved()

    fun insertSavedResult(savedData : ResultEntity){
        viewModelScope.launch {
            resultRepository.insertResultSaved(savedData)
        }
    }

    fun deleteResultById(id: Int) {
        viewModelScope.launch {
            resultRepository.deleteResultById(id)
        }
    }
}