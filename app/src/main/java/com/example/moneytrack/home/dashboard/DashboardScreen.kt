package com.example.moneytrack.home.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.auth.AuthRepository
import com.example.moneytrack.core.components.Screen

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardScreenViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
//    AuthNavigfationLogic(authState = authState, navigateToAuth = navigateToAuth)
    Screen(modifier = modifier.fillMaxSize()) {
        Text(text = authState.toString())
    }
}

@Composable
private fun AuthNavigationLogic(
    authState: AuthRepository.AuthState,
    navigateToAuth: () -> Unit
) {
    LaunchedEffect(authState) {
        if (authState is AuthRepository.AuthState.LoggedOut) {
            navigateToAuth()
        }
    }
}