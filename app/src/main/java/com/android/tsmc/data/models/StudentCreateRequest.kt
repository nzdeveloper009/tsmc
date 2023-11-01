package com.android.tsmc.data.models

data class StudentCreateRequest(
    var email: String,
    var password: String,
    val role: String,
    var username: String
)