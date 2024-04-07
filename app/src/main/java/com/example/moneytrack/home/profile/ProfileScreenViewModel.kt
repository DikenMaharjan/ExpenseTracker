package com.example.moneytrack.home.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthRepository
import com.example.data.category.usecase.AddRandomExpensesUseCase
import com.example.data.preferences.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ProfileScreenViewModel"

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: PreferencesRepository,
    private val addRandomExpensesUseCase: AddRandomExpensesUseCase
) : ViewModel() {

    val authState = authRepository.authState

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    val isDarkMode = userPreferencesRepository.isDarkModeEnabled


    fun logOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _state.update { it.copy(isLogOutSuccess = true) }
        }

    }

    fun toggleTheme() {
        viewModelScope.launch {
            userPreferencesRepository.toggleTheme()
        }
    }


    fun showHideLogoutConfirmation(isShown: Boolean) {
        _state.update { it.copy(isLogOutConfirmationShown = isShown) }
    }

    fun showHideAddExpenseConfirmation(isShown: Boolean) {
        _state.update { it.copy(isFillRandomExpenseConfirmationShown = isShown) }
    }

    fun onSignOutHandled() {
        _state.update { it.copy(isLogOutSuccess = false) }
    }

    fun onFillSuccessHandled() {
        _state.update { it.copy(isAddingRandomExpenseSuccessful = false) }
    }


    fun addRandomExpense() {
        viewModelScope.launch {
            addRandomExpensesUseCase.invoke()
            _state.update { it.copy(isAddingRandomExpenseSuccessful = true) }
        }
    }

    data class State(
        val isLogOutConfirmationShown: Boolean = false,
        val isFillRandomExpenseConfirmationShown: Boolean = false,
        val isLogOutSuccess: Boolean = false,
        val isAddingRandomExpenseSuccessful: Boolean = false
    )
}