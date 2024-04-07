package com.example.moneytrack.auth.otp

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moneytrack.navigation.buildNavigationPath
import com.example.moneytrack.navigation.buildNavigationRoute


private const val OTP_VERIFICATION_ROOT = "OtpVerificationScreen"

private const val TOKEN_KEY = "TokenKey"
private const val VERIFICATION_TYPE = "VerificationTypeKey"

private val otpVerificationRoute: String = buildNavigationRoute(
    root = OTP_VERIFICATION_ROOT,
    keys = listOf(
        TOKEN_KEY,
        VERIFICATION_TYPE
    )
)


fun NavGraphBuilder.otpVerificationScreen(
    navigateToHome: () -> Unit
) {
    composable(
        route = otpVerificationRoute,
        arguments = listOf(
            navArgument(TOKEN_KEY) {
                this.type = NavType.StringType
                this.nullable = false
            },
            navArgument(VERIFICATION_TYPE) {
                this.type = NavType.EnumType(VerificationType::class.java)
                this.nullable = false
            }
        )
    ) {
        OTPVerificationScreen(
            navigateToHome = navigateToHome
        )
    }
}

fun SavedStateHandle.getToken() = this.get<String>(TOKEN_KEY)!!
fun SavedStateHandle.getVerificationType() = this.get<VerificationType>(VERIFICATION_TYPE)!!

fun NavController.navigateToOtpVerification(
    token: String,
    verificationType: VerificationType,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = buildNavigationPath(
            root = OTP_VERIFICATION_ROOT,
            values = listOf(
                token,
                verificationType.toString()
            )
        ),
        navOptions = navOptions
    )
}

enum class VerificationType {
    EmailVerification,
    LoginVerification
}