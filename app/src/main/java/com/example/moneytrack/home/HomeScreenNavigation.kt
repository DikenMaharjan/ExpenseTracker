package com.example.moneytrack.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.moneytrack.auth.navigation.navigateToAuth
import com.example.moneytrack.home.add_expense.navigation.addExpenseBottomSheet
import com.example.moneytrack.home.add_expense.navigation.navigateToAddExpenseBottomSheet
import com.example.moneytrack.home.dashboard.DASHBOARD_SCREEN_ROUTE
import com.example.moneytrack.home.dashboard.dashboardScreen
import com.example.moneytrack.navigation.clearStackNavOptions

const val HOME_ROOT = "HomeRoot"

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation(
        route = HOME_ROOT,
        startDestination = DASHBOARD_SCREEN_ROUTE
    ) {
        dashboardScreen(
            navigateToAuth = navController::navigateToAuth,
            navigateToAddExpenseBottomSheet = navController::navigateToAddExpenseBottomSheet
        )
        addExpenseBottomSheet()
    }
}

fun NavController.navigateToHome() {
    navigate(route = HOME_ROOT, navOptions = clearStackNavOptions)
}

