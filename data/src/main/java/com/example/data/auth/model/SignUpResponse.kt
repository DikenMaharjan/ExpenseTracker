package com.example.data.auth.model

import com.example.network.model.response.SignUpResponseDTO

// The server should provide us the token to verify the email.
// In the current server, the userID is returned which is used to complete verification
data class SignUpResponse(
    val token: String
)

fun SignUpResponseDTO.toSignUpResponse() = SignUpResponse(
    token = userId
)

