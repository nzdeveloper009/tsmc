package com.android.tsmc.data.models

data class User(
    val __v: Int,
    val _id: String,
    var email: String,
    var password: String?=null,
    val role: String,
    var username: String
)