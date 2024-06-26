package com.example.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val userName: String,
    val email: String,
    val password: String
)