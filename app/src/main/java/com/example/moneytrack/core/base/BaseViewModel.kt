package com.example.moneytrack.core.base

import androidx.lifecycle.ViewModel
import com.example.moneytrack.core.model.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel : ViewModel() {
    private val _screenState = MutableStateFlow(
        ScreenState(
            clearError = { clearError() }
        )
    )
    val screenState = _screenState.asStateFlow()

    fun fireErrorMsg(errorMsg: String) {
        _screenState.update { it.copy(errorMsg = errorMsg) }
    }

    private fun clearError() {
        _screenState.update { it.copy(errorMsg = null) }
    }

    fun showLoading(isLoading: Boolean) {
        _screenState.update { it.copy(isLoading = isLoading) }
    }
}