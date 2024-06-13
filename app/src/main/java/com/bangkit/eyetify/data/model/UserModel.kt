package com.bangkit.eyetify.data.model

data class UserModel(
    val email: String,
    val token: String,
    val image: String,
    val isLogin: Boolean = false
)