package com.bangkit.eyetify.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.eyetify.data.injection.AuthInjection
import com.bangkit.eyetify.data.repository.AuthRepository
import com.bangkit.eyetify.ui.viewmodel.model.LoginViewModel
import com.bangkit.eyetify.ui.viewmodel.model.MainViewModel
import com.bangkit.eyetify.ui.viewmodel.model.RegisterViewModel
import com.bangkit.eyetify.ui.viewmodel.model.ResetPasswordViewModel

class AuthViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ResetPasswordViewModel::class.java) -> {
                ResetPasswordViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context) = AuthViewModelFactory(
            AuthInjection.provideAuthRepository(context)
        )
    }
}