package com.example.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class VerifyEmailForRegisterRequest(
    val token: String,
    val otpCode: String
)