package com.example.moneytrack.home.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.FormatColorFill
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.auth.AuthRepository
import com.example.data.model.AppUser
import com.example.moneytrack.core.components.AppDialog
import com.example.moneytrack.home.profile.components.ProfileRowOption
import com.example.moneytrack.home.profile.components.ProfileUserInfo

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Box(modifier = modifier.fillMaxSize()) {
        when (val auth = authState) {
            is AuthRepository.AuthState.LoggedIn -> {
                ProfileScreenContent(
                    appUser = auth.appUser,
                    isDarkMode = isDarkMode,
                    state = state,
                    updateState = viewModel::updateState,
                    logOut = {
                        viewModel.logOut()
                        navigateToAuth()
                    },
                    toggleTheme = viewModel::toggleTheme
                )
            }

            AuthRepository.AuthState.LoggedOut -> {}
        }
    }
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    appUser: AppUser,
    isDarkMode: Boolean,
    state: ProfileScreenViewModel.State,
    updateState: (ProfileScreenViewModel.State) -> Unit,
    logOut: () -> Unit,
    toggleTheme: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ProfileUserInfo(appUser = appUser)
        }

        item {
            ProfileRowOption(
                onClick = {
                    updateState(state.copy(isFillRandomExpenseConfirmationShown = true))
                },
                icon = Icons.Outlined.FormatColorFill,
                title = "Fill random expenses",
                description = "Choosing this option will fill the database with random expenses."
            )
        }

        item {
            ProfileRowOption(
                onClick = toggleTheme,
                icon = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                title = if (isDarkMode) "Switch to light mode?" else "Switch to dark mode?",
                description = ""
            )
        }

        item {
            ProfileRowOption(
                onClick = {
                    updateState(state.copy(isLogOutConfirmationShown = true))
                },
                icon = Icons.AutoMirrored.Outlined.Logout,
                title = "Sign Out",
                description = ""
            )
        }
    }

    if (state.isLogOutConfirmationShown) {
        AppDialog(
            title = "Log Out?",
            description = "Are you sure you want to log out?",
            onConfirm = {
                updateState(state.copy(isLogOutConfirmationShown = false))
                logOut()
            },
            onDismiss = { updateState(state.copy(isLogOutConfirmationShown = false)) },
            confirmText = "Log Out",
            dismissText = "Cancel"
        )
    }


    if (state.isFillRandomExpenseConfirmationShown) {
        AppDialog(
            title = "Fill random expenses?",
            description = "Choosing this option will add about 50 random expenses.(Solely for viewing graphs, contents and functionalities). Do you want to continue?",
            onConfirm = {
                updateState(state.copy(isFillRandomExpenseConfirmationShown = false))
            },
            onDismiss = { updateState(state.copy(isFillRandomExpenseConfirmationShown = false)) },
            confirmText = "Confirm",
            dismissText = "Cancel"
        )
    }
}

