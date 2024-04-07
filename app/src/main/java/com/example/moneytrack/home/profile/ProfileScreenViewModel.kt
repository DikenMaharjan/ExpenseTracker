package com.example.moneytrack.home.profile

import androidx.lifecycle.ViewModel
import com.example.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val authState = authRepository.authState

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    val isDarkMode = MutableStateFlow(false)

    fun updateState(state: State) {
        _state.update { state }
    }

    fun logOut() {


    }

    data class State(
        val isLogOutConfirmationShown: Boolean = false,
        val isFillRandomExpenseConfirmationShown: Boolean = false
    )
}