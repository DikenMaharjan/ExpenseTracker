package com.example.moneytrack.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthRepository
import com.example.data.preferences.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: PreferencesRepository
) : ViewModel() {

    val authState = authRepository.authState

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    val isDarkMode = userPreferencesRepository.isDarkModeEnabled

    fun updateState(state: State) {
        _state.update { state }
    }

    fun logOut() {


    }

    fun toggleTheme() {
        viewModelScope.launch {
            userPreferencesRepository.toggleTheme()
        }
    }

    data class State(
        val isLogOutConfirmationShown: Boolean = false,
        val isFillRandomExpenseConfirmationShown: Boolean = false
    )
}