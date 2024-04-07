package com.example.moneytrack.home.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val DASHBOARD_SCREEN_ROUTE = "DashboardScreenRoute"

fun NavGraphBuilder.dashboardScreen(
    navigateToAddExpenseBottomSheet: () -> Unit,
    navigateToProfile: () -> Unit
) {
    composable(DASHBOARD_SCREEN_ROUTE) {
        DashboardScreen(
            navigateToAddExpenseBottomSheet = navigateToAddExpenseBottomSheet,
            navigateToProfile = navigateToProfile
        )
    }
}