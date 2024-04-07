package com.example.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class VerifyEmailForRegisterResponseDTO(
    val id: String,
    val message: String,
    val userName: String
)