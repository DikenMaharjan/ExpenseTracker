package com.example.moneytrack.auth.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moneytrack.core.components.Screen

@Composable
fun OTPVerificationScreen(
    modifier: Modifier = Modifier,
    viewModel: OTPVerificationScreenViewModel = hiltViewModel()
) {
    Screen(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(text = "Verification Screen")
    }
}