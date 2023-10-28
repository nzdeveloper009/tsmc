package com.android.tsmc.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "TSMCPreferences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // Initialize the SharedPreferences instance.
    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    // User login status
    var isUserLoggedIn: Boolean
        get() = preferences.getBoolean("isUserLoggedIn", false)
        set(value) = preferences.edit().putBoolean("isUserLoggedIn", value).apply()

    // User ID
    var userId: String
        get() = preferences.getString("userId", "") ?: ""
        set(value) = preferences.edit().putString("userId", value).apply()

    // User Name
    var userName: String
        get() = preferences.getString("userName", "") ?: ""
        set(value) = preferences.edit().putString("userName", value).apply()
    // User Type
    var userType: String
        get() = preferences.getString("userType", "") ?: ""
        set(value) = preferences.edit().putString("userType", value).apply()
}