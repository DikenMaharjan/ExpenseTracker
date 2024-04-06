package com.example.moneytrack.auth.signin

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.moneytrack.auth.signup.navigateToSignUp

const val SIGN_IN_ROUTE = "SignInRoute"

fun NavGraphBuilder.signInScreen(
    navController: NavController
) {
    composable(SIGN_IN_ROUTE) {
        SignInScreen(
            navigateToSignUp = navController::navigateToSignUp,
            navigateToOTPVerification = {}
        )
    }
}

fun NavController.navigateToSignIn() {
    navigate(SIGN_IN_ROUTE)
}