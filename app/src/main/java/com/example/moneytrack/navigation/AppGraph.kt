package com.example.moneytrack.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.data.auth.AuthRepository
import com.example.moneytrack.MainActivityViewModel
import com.example.moneytrack.auth.navigation.AUTH_ROOT
import com.example.moneytrack.auth.navigation.authGraph
import com.example.moneytrack.home.HOME_ROOT
import com.example.moneytrack.home.homeGraph

const val ROOT = "Root"

fun NavGraphBuilder.appGraph(
    navController: NavController,
    viewModel: MainActivityViewModel
) {
    navigation(
        route = ROOT,
        startDestination = if (viewModel.initialAuthState is AuthRepository.AuthState.LoggedIn) {
            HOME_ROOT
        } else {
            AUTH_ROOT
        }
    ) {
        authGraph(
            navController = navController
        )
        homeGraph(
            navController = navController
        )
    }
}