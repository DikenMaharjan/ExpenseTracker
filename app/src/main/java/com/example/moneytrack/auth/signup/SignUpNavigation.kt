package com.example.moneytrack.auth.signup

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SIGN_UP_ROUTE = "SignUpRoute"

fun NavGraphBuilder.signUpScreen(
    navController: NavController
) {
    composable(
        route = SIGN_UP_ROUTE,
        enterTransition = {
            slideInHorizontally { it }
        },
        exitTransition = {
            slideOutHorizontally { it }
        }
    ) {
        SignUpScreen(
            navigateBack = navController::navigateUp,
            navigateToOtpVerification = {}
        )
    }
}

fun NavController.navigateToSignUp() {
    navigate(SIGN_UP_ROUTE)
}