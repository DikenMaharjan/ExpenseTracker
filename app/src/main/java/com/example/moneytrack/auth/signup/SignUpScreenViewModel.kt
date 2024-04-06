package com.example.moneytrack.auth.signup

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthRepository
import com.example.data.model.errorMsg
import com.example.moneytrack.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            showLoading(true)
            val response = authRepository.signUp(
                userName = name, email = email, password = password
            )
            showLoading(false)

            response
                .onSuccess {
                    _state.update { it.copy(signUpSuccess = true) }
                }.onFailure {
                    fireErrorMsg(it.errorMsg)
                }
        }
    }

    fun onSignUpSuccessHandled() {
        _state.update { it.copy(signUpSuccess = false) }
    }

    @Immutable
    data class State(
        val signUpSuccess: Boolean = false
    )

}