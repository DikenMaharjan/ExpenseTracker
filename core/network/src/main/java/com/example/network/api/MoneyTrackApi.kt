package com.example.network.api

import com.example.network.model.login.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface MoneyTrackApi {

    @POST("/api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Unit
}