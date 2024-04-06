package com.example.network.model.common

import kotlinx.serialization.Serializable

@Serializable
data class NetworkError(
    val error_message: String
)