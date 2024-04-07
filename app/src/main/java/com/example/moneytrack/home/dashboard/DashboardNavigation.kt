package com.example.moneytrack.home.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val DASHBOARD_SCREEN_ROUTE = "DashboardScreenRoute"

fun NavGraphBuilder.dashboardScreen(
    navigateToAuth: () -> Unit,
    navigateToAddExpenseBottomSheet: () -> Unit
) {
    composable(DASHBOARD_SCREEN_ROUTE) {
        DashboardScreen(
            navigateToAuth = navigateToAuth,
            navigateToAddExpenseBottomSheet = navigateToAddExpenseBottomSheet
        )
    }
}