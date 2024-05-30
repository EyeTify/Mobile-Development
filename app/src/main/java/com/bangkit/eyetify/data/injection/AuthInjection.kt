package com.bangkit.eyetify.data.injection

import android.content.Context
import com.bangkit.eyetify.data.preference.UserPreference
import com.bangkit.eyetify.data.preference.dataStore
import com.bangkit.eyetify.data.repository.AuthRepository
import com.bangkit.eyetify.data.retrofit.AuthConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object AuthInjection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { userPreference.getLoginSession().first() }
        val authApiService = AuthConfig.getApiService(user.token)
        return AuthRepository(authApiService, userPreference)
    }
}