package com.example.moneytrack.auth.signup

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.moneytrack.auth.otp.VerificationType
import com.example.moneytrack.auth.otp.navigateToOtpVerification

const val SIGN_UP_ROUTE = "SignUpRoute"

fun NavGraphBuilder.signUpScreen(
    navController: NavController
) {
    composable(
        route = SIGN_UP_ROUTE,
        enterTransition = {
            slideInHorizontally { it }
        },
        popExitTransition = {
            slideOutHorizontally { it }
        },
        exitTransition = null,
        popEnterTransition = null
    ) {
        SignUpScreen(
            navigateBack = navController::navigateUp,
            navigateToOtpVerification = { token ->
                navController.navigateToOtpVerification(
                    token = token,
                    verificationType = VerificationType.EmailVerification
                )
            }
        )
    }
}

fun NavController.navigateToSignUp() {
    navigate(SIGN_UP_ROUTE)
}