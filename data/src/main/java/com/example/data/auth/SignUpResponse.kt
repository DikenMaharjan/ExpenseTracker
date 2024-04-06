package com.example.data.auth

import com.example.network.model.response.SignUpResponseDTO

// The server should provide us the token to verify the email.
// In my personal server, the userID is returned which is used to complete verificaiton
data class SignUpResponse(
    val token: String
)

fun SignUpResponseDTO.toSignUpResponse() = SignUpResponse(
    token = userId
)

