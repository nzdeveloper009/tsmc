package com.android.tsmc.data.api

import com.android.tsmc.data.models.UserLoginRequest
import com.android.tsmc.data.models.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/api/auth/login")
    suspend fun userLogin(@Body userLoginRequest: UserLoginRequest): Response<UserLoginResponse>
}