package com.example.network.datasource

import com.example.network.api.MoneyTrackApi
import com.example.network.model.request.SignInRequest
import com.example.network.model.request.SignUpRequest
import com.example.network.model.request.VerifyOTPRequest
import com.example.network.model.response.SignInResponseDTO
import com.example.network.model.response.SignUpResponseDTO
import com.example.network.model.response.VerifyOTPResponseDTO
import com.example.network.utils.SafeApiCall
import javax.inject.Inject

class RemoteAuthDataSource @Inject constructor(
    private val moneyTrackApi: MoneyTrackApi,
    private val safeApiCall: SafeApiCall
) {
    suspend fun signIn(email: String, password: String): Result<SignInResponseDTO> {
        return safeApiCall {
            moneyTrackApi.signIn(
                signInRequest = SignInRequest(
                    email = email,
                    password = password
                )
            )
        }
    }

    suspend fun signUp(
        userName: String,
        email: String,
        password: String
    ): Result<SignUpResponseDTO> {
        return safeApiCall {
            moneyTrackApi.signUp(
                signUpRequest = SignUpRequest(
                    userName = userName,
                    email = email,
                    password = password
                )
            )
        }
    }

    suspend fun verifyOTP(
        token: String,
        otpCode: String
    ): Result<VerifyOTPResponseDTO> {
        return safeApiCall {
            moneyTrackApi.verifyOTP(
                verifyOTPRequest = VerifyOTPRequest(
                    token = token,
                    otpCode = otpCode
                )
            )
        }
    }
}