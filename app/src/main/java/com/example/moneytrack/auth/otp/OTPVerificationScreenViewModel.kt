package com.example.moneytrack.auth.otp

import androidx.lifecycle.SavedStateHandle
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
class OTPVerificationScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    private val token = savedStateHandle.getToken()
    val verificationType = savedStateHandle.getVerificationType()

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun submitOtp() {
        viewModelScope.launch {
            val otpCode = state.value.otpCode
            if (otpCode.length == OTP_LENGTH) {
                showLoading(true)
                val response = authRepository.verifyEmailOnSignUp(token = token, otpCode = otpCode)
                showLoading(false)

                response
                    .onSuccess {
                        _state.update { it.copy(isVerificationSuccess = true) }
                    }.onFailure {
                        fireErrorMsg(it.errorMsg)
                    }
            } else {
                _state.update { it.copy(otpError = true) }
            }
        }
    }


    fun onOtpChange(otpCode: String) {
        if (otpCode.length <= OTP_LENGTH) {
            _state.update { it.copy(otpCode = otpCode, otpError = false) }
        }
    }

    fun onVerificationHandled() {
        _state.update { it.copy(isVerificationSuccess = false) }
    }

    data class State(
        val otpCode: String = "",
        val otpError: Boolean = false,
        val isVerificationSuccess: Boolean = false
    )

    companion object {
        const val OTP_LENGTH = 6
    }

}