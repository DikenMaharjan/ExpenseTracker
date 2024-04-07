package com.example.moneytrack.home.dashboard

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.moneytrack.auth.navigation.navigateToAuth

const val DASHBOARD_SCREEN_ROUTE = "DashboardScreenRoute"

fun NavGraphBuilder.dashboardScreen(
    navController: NavController
) {
    composable(DASHBOARD_SCREEN_ROUTE) {
        DashboardScreen(
            navigateToAuth = navController::navigateToAuth
        )
    }
}