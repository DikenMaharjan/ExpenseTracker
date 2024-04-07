package com.example.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponseDTO(
    val id: String,
    val requiresVerification: Boolean,
    val userName: String
)