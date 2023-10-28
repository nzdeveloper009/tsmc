package com.android.tsmc.data.models

data class UserLoginResponse(
    val success: Boolean,
    val token: String,
    val user: User
)