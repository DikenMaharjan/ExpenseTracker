package com.example.moneytrack.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.moneytrack.auth.navigation.AUTH_ROUTE
import com.example.moneytrack.auth.navigation.authGraph
import com.example.moneytrack.home.homeGraph

const val ROOT = "Root"
fun NavGraphBuilder.appGraph(navController: NavController) {
    navigation(
        route = ROOT,
        startDestination = AUTH_ROUTE
    ) {
        authGraph(
            navController = navController
        )
        homeGraph(
            navController = navController
        )
    }
}