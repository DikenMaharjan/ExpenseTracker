package com.example.data.auth.model

import com.example.network.model.response.SignInResponseDTO

sealed class SignInResponse {
    data class RequiresVerification(val token: String) : SignInResponse()
    data object Success : SignInResponse()


}

fun SignInResponseDTO.toSignInResponse(): SignInResponse {
    return if (this.requiresVerification) {
        SignInResponse.RequiresVerification(
            token = this.id
        )
    } else {
        SignInResponse.Success
    }
}