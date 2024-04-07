package com.example.moneytrack.home.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.moneytrack.home.profile.ProfileScreen

const val PROFILE_ROUTE = "ProfileRoute"
fun NavGraphBuilder.profileScreen() {
    composable(PROFILE_ROUTE) {
        ProfileScreen()
    }
}

fun NavController.navigateToProfile() {
    navigate(PROFILE_ROUTE)
}