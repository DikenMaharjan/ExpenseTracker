package com.example.moneytrack.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import com.example.moneytrack.auth.signup.components.SignUpButtonRow
import com.example.moneytrack.auth.signup.components.SignUpTextFields
import com.example.moneytrack.auth.signup.components.SignUpTitle
import com.example.moneytrack.core.components.Screen
import com.example.moneytrack.core.components.textfield.AppTextFieldState
import com.example.moneytrack.core.components.textfield.TextType
import com.example.moneytrack.core.components.textfield.rememberTextFieldState
import com.example.moneytrack.core.model.ScreenState
import com.example.moneytrack.ui.theme.LocalDimens
import com.example.moneytrack.ui.theme.MoneyTrackTheme

private const val TAG = "SignUpScreen"

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToOtpVerification: (token: String) -> Unit
) {
    val userNameTextFieldState = rememberTextFieldState(
        hint = "Enter your name",
        textType = TextType.Username
    )
    val emailTextFieldState = rememberTextFieldState(
        hint = "Enter your email",
        textType = TextType.Email
    )
    val passwordTextFieldState = rememberTextFieldState(
        hint = "Password",
        textType = TextType.Password
    )
    val confirmPasswordTextFieldState =
        rememberTextFieldState(
            hint = "Please re-enter your password",
            textType = TextType.Password
        )

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.signUpToken) {
        val token = state.signUpToken
        if (token != null) {
            navigateToOtpVerification(token)
            viewModel.onSignUpSuccessHandled()
        }
    }
    SignUpScreenContent(
        modifier = modifier.fillMaxSize(),
        screenState = screenState,
        userNameTextFieldState = userNameTextFieldState,
        emailTextFieldState = emailTextFieldState,
        passwordTextFieldState = passwordTextFieldState,
        confirmPasswordTextFieldState = confirmPasswordTextFieldState,
        navigateBack = navigateBack,
        signUp = viewModel::signUp
    )
}

@Composable
private fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    screenState: ScreenState,
    userNameTextFieldState: AppTextFieldState,
    emailTextFieldState: AppTextFieldState,
    passwordTextFieldState: AppTextFieldState,
    confirmPasswordTextFieldState: AppTextFieldState,
    navigateBack: () -> Unit,
    signUp: (userName: String, email: String, password: String) -> Unit
) {
    val localKeyboardController = LocalSoftwareKeyboardController.current

    Screen(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        screenState = screenState,
        showBack = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            SignUpTitle()

            SignUpTextFields(
                userNameTextFieldState = userNameTextFieldState,
                emailTextFieldState = emailTextFieldState,
                passwordTextFieldState = passwordTextFieldState,
                confirmPasswordTextFieldState = confirmPasswordTextFieldState
            )

            Spacer(modifier = Modifier.height(LocalDimens.current.dimen48))

            SignUpButtonRow(
                navigateBack = navigateBack,
                signUp = {
                    if (userNameTextFieldState.isValid &&
                        passwordTextFieldState.isValid &&
                        emailTextFieldState.isValid &&
                        confirmPasswordTextFieldState.equalsText(passwordTextFieldState.text) {
                            "Password do not match."
                        }
                    ) {
                        signUp(
                            userNameTextFieldState.text,
                            emailTextFieldState.text,
                            passwordTextFieldState.text
                        )
                        localKeyboardController?.hide()
                    }
                }
            )
            Spacer(modifier = Modifier)
        }
    }
}


@Preview
@Composable
private fun SignUpScreenContentPreview() {
    MoneyTrackTheme {
        SignUpScreenContent(
            screenState = ScreenState(),
            userNameTextFieldState = rememberTextFieldState(hint = "UserName"),
            emailTextFieldState = rememberTextFieldState(hint = "Email"),
            passwordTextFieldState = rememberTextFieldState(hint = "Password"),
            confirmPasswordTextFieldState = rememberTextFieldState(hint = "Confrim Password"),
            navigateBack = { /*TODO*/ },
            signUp = { _, _, _ -> }
        )
    }

}
