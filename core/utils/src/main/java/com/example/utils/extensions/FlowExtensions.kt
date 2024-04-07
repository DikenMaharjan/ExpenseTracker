package com.example.utils.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

context(ViewModel)
fun <T> Flow<T>.stateInWhileSubscribed(initialValue: T): StateFlow<T> {
    return this.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue
    )
}