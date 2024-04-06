package com.example.moneytrack.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.moneytrack.auth.otp.otpVerificationScreen
import com.example.moneytrack.auth.signin.SIGN_IN_ROUTE
import com.example.moneytrack.auth.signin.signInScreen
import com.example.moneytrack.auth.signup.signUpScreen

const val AUTH_ROUTE = "Auth"

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation(route = AUTH_ROUTE, startDestination = SIGN_IN_ROUTE) {
        signInScreen(navController)
        signUpScreen(navController)
        otpVerificationScreen()
    }
}

fun NavController.navigateToAuth() {
    navigate(AUTH_ROUTE)
}