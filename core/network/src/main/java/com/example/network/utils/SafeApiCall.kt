package com.example.network.utils

import com.example.network.model.common.NetworkError
import com.example.utils.dispatcher.AppDispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import retrofit2.Response
import javax.inject.Inject

class SafeApiCall @Inject constructor(
    private val appDispatchers: AppDispatchers
) {
    suspend operator fun <T> invoke(invoke: suspend () -> Response<T>): Result<T> {
        return withContext(appDispatchers.background) {
            try {
                val response = invoke()
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(
                        Exception(
                            Json.decodeFromString<NetworkError>(
                                response.errorBody()?.string() ?: "Something went wrong."
                            ).error_message
                        )
                    )
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}