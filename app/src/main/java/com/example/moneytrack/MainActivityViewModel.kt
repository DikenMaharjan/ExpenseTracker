package com.example.moneytrack

import androidx.lifecycle.ViewModel
import com.example.data.auth.AuthRepository
import com.example.data.preferences.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    authRepository: AuthRepository,
    preferencesRepository: PreferencesRepository
) : ViewModel() {

    val initialAuthState = authRepository.authState.value

    val isDarkMode = preferencesRepository.isDarkModeEnabled
}