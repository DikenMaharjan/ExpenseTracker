package com.example.moneytrack

import androidx.lifecycle.ViewModel
import com.example.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {

    val initialAuthState = authRepository.authState.value
}