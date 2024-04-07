package com.example.moneytrack.auth.signin

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.moneytrack.auth.otp.VerificationType
import com.example.moneytrack.auth.otp.navigateToOtpVerification
import com.example.moneytrack.auth.signup.navigateToSignUp
import com.example.moneytrack.home.navigateToHome

const val SIGN_IN_ROUTE = "SignInRoute"

private const val TAG = "SignInNavigation"

fun NavGraphBuilder.signInScreen(
    navController: NavController
) {
    composable(SIGN_IN_ROUTE) {
        SignInScreen(
            navigateToSignUp = navController::navigateToSignUp,
            navigateToOTPVerification = { token ->
                Log.e(TAG, "signInScreen: $token", )
                navController.navigateToOtpVerification(
                    token = token,
                    verificationType = VerificationType.LoginVerification
                )
            },
            navigateToHome = navController::navigateToHome
        )
    }
}

fun NavController.navigateToSignIn() {
    navigate(SIGN_IN_ROUTE)
}