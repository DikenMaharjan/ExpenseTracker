package com.example.data.auth

import com.example.data.model.AppUser
import com.example.datastore.model.UserDataProto
import com.example.datastore.proto.UserDataDataSource
import com.example.network.datasource.RemoteAuthDataSource
import com.example.network.model.response.SignUpResponseDTO
import com.example.utils.di.ApplicationScope
import com.example.utils.extensions.toUnitResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val userDataDataSource: UserDataDataSource,
    @ApplicationScope private val scope: CoroutineScope
) {

    val authState = userDataDataSource.userData
        .map(UserDataProto?::toAuthState)
        .stateIn(scope, SharingStarted.Lazily, AuthState.LoggedOut)

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
                userDataDataSource.storeUserData(
                    name = it.userName,
                    id = it.id
                )
            }
        return response.toUnitResult()
    }

    fun signOut(): Result<Unit> {
        return kotlin.runCatching { }
    }

    sealed interface AuthState {
        data class LoggedIn(val appUser: AppUser) : AuthState
        data object LoggedOut : AuthState
    }

}

private fun UserDataProto?.toAuthState() =
    if (this == null) AuthRepository.AuthState.LoggedOut else AuthRepository.AuthState.LoggedIn(this.toAppUser())

private fun UserDataProto.toAppUser() = AppUser(name = this.name)