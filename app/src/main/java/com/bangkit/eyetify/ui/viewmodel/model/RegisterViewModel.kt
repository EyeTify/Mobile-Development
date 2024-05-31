package com.bangkit.eyetify.ui.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.eyetify.data.repository.AuthRepository
import com.bangkit.eyetify.data.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    val signUpResponse: LiveData<RegisterResponse> = repository.registerResponse

    fun register(name: String, email: String, pass: String){
        viewModelScope.launch {
            repository.register(name, email, pass)
        }
    }
}