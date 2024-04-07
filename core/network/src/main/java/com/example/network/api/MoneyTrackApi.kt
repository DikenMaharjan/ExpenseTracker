package com.example.network.api

import com.example.network.model.request.SignInRequest
import com.example.network.model.request.SignUpRequest
import com.example.network.model.request.VerifyOTPRequest
import com.example.network.model.response.SignInResponseDTO
import com.example.network.model.response.SignUpResponseDTO
import com.example.network.model.response.VerifyOTPResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MoneyTrackApi {

    @POST("/api/signIn")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): Response<SignInResponseDTO>

    @POST("/api/signUp")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<SignUpResponseDTO>

    @POST("/api/verify")
    suspend fun verifyOTP(
        @Body verifyOTPRequest: VerifyOTPRequest
    ): Response<VerifyOTPResponseDTO>
}