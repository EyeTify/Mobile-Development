package com.bangkit.eyetify.ui.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.eyetify.data.model.UserModel
import com.bangkit.eyetify.data.preference.Result
import com.bangkit.eyetify.data.repository.AuthRepository
import com.bangkit.eyetify.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> =_loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Result.DataLoading
            val result = repository.login(email, password)
            _loginResult.value = result
        }
    }

    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            repository.saveLoginSession(userModel)
        }
    }
}