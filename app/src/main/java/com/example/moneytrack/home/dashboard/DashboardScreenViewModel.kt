package com.example.moneytrack.home.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeScreenViewModel"

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val authState = authRepository.authState

}