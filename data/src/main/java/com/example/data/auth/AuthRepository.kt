package com.example.data.auth

import com.example.data.model.AppUser
import com.example.network.datasource.RemoteAuthDataSource
import com.example.network.model.response.SignUpResponseDTO
import com.example.utils.extensions.toUnitResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource
) {

    suspend fun signUp(userName: String, email: String, password: String): Result<SignUpResponse> {
        return remoteAuthDataSource.signUp(
            userName = userName,
            email = email,
            password = password
        ).map(SignUpResponseDTO::toSignUpResponse)
    }

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return remoteAuthDataSource.signIn(
            email = email,
            password = password
        )
    }

    suspend fun verifyEmailOnSignUp(token: String, otpCode: String): Result<Unit> {
        val response = remoteAuthDataSource.verifyEmailOnSignUp(
            token = token,
            otpCode = otpCode
        )
        response
            .onSuccess {

            }
        return response.toUnitResult()
    }

    fun signOut(): Result<Unit> {
        return kotlin.runCatching { }
    }

    sealed interface AuthState {
        class LoggedIn(val appUser: AppUser) : AuthState
        data object LoggedOut : AuthState
    }

}