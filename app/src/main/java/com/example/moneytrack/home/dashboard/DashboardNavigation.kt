package com.example.moneytrack.home.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val DASHBOARD_SCREEN_ROUTE = "DashboardScreenRoute"
fun NavGraphBuilder.dashboardScreen() {
    composable(DASHBOARD_SCREEN_ROUTE) {
        DashboardScreen()
    }
}