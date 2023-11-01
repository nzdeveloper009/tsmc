package com.android.tsmc

import android.app.Application
import com.android.tsmc.utils.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TeacherStudentManagementCourseApp : Application(){
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(applicationContext)
    }
}