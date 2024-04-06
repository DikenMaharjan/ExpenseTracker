package com.example.moneytrack.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(
                email = email,
                password = password
            )
        }

    }

}