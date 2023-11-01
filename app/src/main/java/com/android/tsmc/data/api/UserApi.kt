package com.android.tsmc.data.api

import com.android.tsmc.data.models.StudentCreateRequest
import com.android.tsmc.data.models.StudentCreateResponse
import com.android.tsmc.data.models.User
import com.android.tsmc.data.models.UserLoginRequest
import com.android.tsmc.data.models.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST("/api/auth/login")
    suspend fun userLogin(
        @Body userLoginRequest: UserLoginRequest
    ): Response<UserLoginResponse>

    @POST("/api/admin/createUser")
    suspend fun createStudent(
        @Header("Authorization") token: String,
        @Body studentCreateRequest: StudentCreateRequest
    ): Response<StudentCreateResponse>
    @GET("/api/admin/students-list")
    suspend fun getAllStudent(
        @Header("Authorization") token: String,
    ): Response<List<User>>
}