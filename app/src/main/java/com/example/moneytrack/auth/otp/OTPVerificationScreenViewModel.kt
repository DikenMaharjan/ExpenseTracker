package com.example.moneytrack.auth.otp

import androidx.lifecycle.SavedStateHandle
import com.example.moneytrack.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OTPVerificationScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val token = savedStateHandle.getToken()
    val verificationType = savedStateHandle.getVerificationType()


}