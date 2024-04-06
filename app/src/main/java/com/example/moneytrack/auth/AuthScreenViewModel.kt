package com.example.moneytrack.auth

import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthRepository
import com.example.moneytrack.core.base.BaseViewModel
import com.example.data.model.errorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            showLoading(true)
            val response = authRepository.signIn(email = email, password = password)
            showLoading(false)

            response.fold(
                onSuccess = {
                    _state.update { it.copy(isAuthSuccessful = true) }
                },
                onFailure = {
                    fireErrorMsg(it.errorMsg)
                }
            )
        }
    }

    fun signUp(userName: String, email: String, password: String) {
        viewModelScope.launch {
            showLoading(true)
            val response =
                authRepository.signUp(userName = userName, email = email, password = password)
            showLoading(false)

            response.fold(
                onSuccess = {
                    _state.update { it.copy(isAuthSuccessful = true) }
                },
                onFailure = {
                    fireErrorMsg(it.errorMsg)
                }
            )
        }
    }

    fun signUpInstead() {
        _state.update { it.copy(authMethod = AuthMethod.SignUp) }
    }

    fun backToSignIn() {
        _state.update { it.copy(authMethod = AuthMethod.SignIn) }
    }

    fun onAuthSuccessHandled() {
        _state.update { it.copy(isAuthSuccessful = false) }
    }

    @Immutable
    data class State(
        val isAuthSuccessful: Boolean = false,
        val authMethod: AuthMethod = AuthMethod.SignIn
    )

    enum class AuthMethod {
        SignIn, SignUp
    }

}