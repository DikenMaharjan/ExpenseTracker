package com.example.moneytrack.auth.signin

import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthRepository
import com.example.data.auth.model.SignInResponse
import com.example.data.model.errorMsg
import com.example.moneytrack.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            showLoading(true)
            val response = authRepository.signIn(
                email = email,
                password = password
            )
            showLoading(false)

            response
                .onSuccess { signInResponse ->
                    when (signInResponse) {
                        is SignInResponse.RequiresVerification -> {
                            _state.update { it.copy(verificationToken = signInResponse.token) }
                        }

                        SignInResponse.Success -> {
                            _state.update { it.copy(success = true) }
                        }
                    }
                }.onFailure {
                    fireErrorMsg(it.errorMsg)
                }
        }
    }

    fun onSuccessHandled() {
        _state.update { it.copy(success = false) }
    }

    fun onVerificationNeededHandled() {
        _state.update { it.copy(verificationToken = null) }
    }

    data class State(
        val verificationToken: String? = null,
        val success: Boolean = false
    )

}