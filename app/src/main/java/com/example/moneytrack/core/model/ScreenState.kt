package com.example.moneytrack.core.model

data class ScreenState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val clearError: () -> Unit = {}
)
