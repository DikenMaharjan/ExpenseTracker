package com.example.network.api

import com.example.network.model.request.SignInRequest
import com.example.network.model.request.SignUpRequest
import com.example.network.model.response.SignUpResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MoneyTrackApi {

    @POST("/api/signIn")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): Response<Unit>

    @POST("/api/signUp")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<SignUpResponseDTO>
}