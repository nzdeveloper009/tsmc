package com.android.tsmc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TeacherStudentManagementCourseApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}