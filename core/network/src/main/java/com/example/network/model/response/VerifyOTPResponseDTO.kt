package com.example.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOTPResponseDTO(
    val id: String,
    val message: String,
    val userName: String
)