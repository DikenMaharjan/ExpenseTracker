package com.example.moneytrack.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.moneytrack.auth.otp.otpVerificationScreen
import com.example.moneytrack.auth.signin.SIGN_IN_ROUTE
import com.example.moneytrack.auth.signin.signInScreen
import com.example.moneytrack.auth.signup.signUpScreen
import com.example.moneytrack.home.navigateToHome
import com.example.moneytrack.navigation.clearStackNavOptions

const val AUTH_ROOT = "Auth"

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation(route = AUTH_ROOT, startDestination = SIGN_IN_ROUTE) {
        signInScreen(navController)
        signUpScreen(navController)
        otpVerificationScreen(
            navigateToHome = navController::navigateToHome
        )
    }
}

fun NavController.navigateToAuth() {
    navigate(route = AUTH_ROOT, navOptions = clearStackNavOptions)
}