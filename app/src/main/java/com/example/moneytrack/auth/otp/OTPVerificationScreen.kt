package com.example.moneytrack.auth.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moneytrack.auth.otp.VerificationType.EmailVerification
import com.example.moneytrack.auth.otp.VerificationType.LoginVerification
import com.example.moneytrack.auth.otp.components.OTPTextField
import com.example.moneytrack.core.components.AppButton
import com.example.moneytrack.core.components.Screen
import com.example.moneytrack.ui.theme.LocalDimens

@Composable
fun OTPVerificationScreen(
    modifier: Modifier = Modifier,
    viewModel: OTPVerificationScreenViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.isVerificationSuccess) {
        if (state.isVerificationSuccess) {
            navigateToHome()
            viewModel.onVerificationHandled()
        }
    }
    Screen(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        screenState = screenState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalDimens.current.dimen24)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerificationTitle(
                verificationType = viewModel.verificationType
            )

            OTPTextField(
                otpCode = state.otpCode,
                onValueChange = viewModel::onOtpChange,
                isError = state.otpError
            )
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = viewModel::submitOtp,
                text = "Submit"
            )
            Spacer(modifier = Modifier)
        }
    }
}

@Composable
private fun VerificationTitle(
    modifier: Modifier = Modifier,
    verificationType: VerificationType
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = when (verificationType) {
                EmailVerification -> "Please verify your email."
                LoginVerification -> "Please verify your identity."
            },
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen8))
        Text(
            text = when (verificationType) {
                EmailVerification -> "We have sent you an email with the verification code."
                LoginVerification -> "Please check you email. We will be providing you with an OTP code for one time verification."
            },
            style = MaterialTheme.typography.titleMedium
        )
    }

}