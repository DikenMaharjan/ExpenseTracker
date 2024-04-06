package com.example.network.datasource

import com.example.network.api.MoneyTrackApi
import com.example.network.model.login.LoginRequest
import javax.inject.Inject

class RemoteAuthDataSource @Inject constructor(
    private val moneyTrackApi: MoneyTrackApi
) {
    suspend fun login(email: String, password: String): Result<Unit> {
        return kotlin.runCatching {
            moneyTrackApi.login(
                loginRequest = LoginRequest(
                    email = email,
                    password = password
                )
            )
        }
    }
}