package com.example.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponseDTO(
    val email: String,
    val message: String,
    val userId: String
)