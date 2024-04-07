package com.example.moneytrack.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moneytrack.auth.signin.components.SignInButtonContent
import com.example.moneytrack.auth.signin.components.SignInTextFields
import com.example.moneytrack.auth.signin.components.SignInTitle
import com.example.moneytrack.core.components.Screen
import com.example.moneytrack.core.components.textfield.AppTextFieldState
import com.example.moneytrack.core.components.textfield.TextType
import com.example.moneytrack.core.components.textfield.rememberTextFieldState
import com.example.moneytrack.core.model.ScreenState
import com.example.moneytrack.ui.theme.LocalDimens
import com.example.moneytrack.ui.theme.MoneyTrackTheme

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInScreenViewModel = hiltViewModel(),
    navigateToSignUp: () -> Unit,
    navigateToOTPVerification: (String) -> Unit,
    navigateToHome: () -> Unit
) {
    val emailTextFieldState =
        rememberTextFieldState(hint = "Enter your email", textType = TextType.Email)
    val passwordTextFieldState =
        rememberTextFieldState(hint = "Password", textType = TextType.Password)
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(
        state.verificationToken,
    ) {
        val verificationToken = state.verificationToken
        if (verificationToken != null) {
            navigateToOTPVerification(verificationToken)
            viewModel.onVerificationNeededHandled()
        }
    }

    LaunchedEffect(state.success) {
        if (state.success) {
            navigateToHome()
            viewModel.onSuccessHandled()
        }
    }
    SignInScreenContent(
        modifier = modifier.fillMaxSize(),
        screenState = screenState,
        emailTextFieldState = emailTextFieldState,
        passwordTextFieldState = passwordTextFieldState,
        signIn = { email, password ->
            viewModel.signIn(email, password)
        },
        navigateToSignUp = navigateToSignUp
    )
}

@Composable
private fun SignInScreenContent(
    modifier: Modifier = Modifier,
    screenState: ScreenState,
    emailTextFieldState: AppTextFieldState,
    passwordTextFieldState: AppTextFieldState,
    signIn: (email: String, password: String) -> Unit,
    navigateToSignUp: () -> Unit
) {
    val localKeyboardController = LocalSoftwareKeyboardController.current

    Screen(
        modifier = modifier
            .fillMaxSize(),
        screenState = screenState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .imePadding(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            SignInTitle()

            Spacer(modifier = Modifier.height(LocalDimens.current.dimen24))
            SignInTextFields(
                emailTextFieldState = emailTextFieldState,
                passwordTextFieldState = passwordTextFieldState
            )
            Spacer(modifier = Modifier.height(12.dp))

            SignInButtonContent(
                onClick = {
                    if (emailTextFieldState.isValid && passwordTextFieldState.isValid) {
                        signIn(emailTextFieldState.text, passwordTextFieldState.text)
                        localKeyboardController?.hide()
                    }
                },
                navigateToSignUp = navigateToSignUp
            )

            Spacer(modifier = Modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenContentPreview() {
    MoneyTrackTheme {
        SignInScreenContent(
            screenState = ScreenState(),
            emailTextFieldState = rememberTextFieldState(hint = "Email"),
            passwordTextFieldState = rememberTextFieldState(hint = "Password"),
            signIn = { _, _ -> },
            navigateToSignUp = {}
        )
    }
}

