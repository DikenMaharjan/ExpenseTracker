package com.example.moneytrack.home.profile

import androidx.lifecycle.ViewModel
import com.example.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val authState = authRepository.authState

}