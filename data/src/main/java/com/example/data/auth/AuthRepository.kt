package com.example.data.auth

import com.example.data.model.AppUser

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
) {

    suspend fun signUp(userName: String, email: String, password: String): Result<Unit> {
        return kotlin.runCatching { }
    }

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return runCatching {
            Unit
        }
    }

    fun signOut(): Result<Unit> {
        return runCatching {

        }
    }

    sealed interface AuthState {
        class LoggedIn(val appUser: AppUser) : AuthState
        data object LoggedOut : AuthState
    }

}