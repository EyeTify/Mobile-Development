package com.bangkit.eyetify.ui.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.eyetify.data.preference.Result
import com.bangkit.eyetify.data.repository.AuthRepository
import com.bangkit.eyetify.data.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _registeResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> =_registeResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registeResult.value = Result.DataLoading
            val result = repository.register(name, email, password)
            _registeResult.value = result
        }
    }
}