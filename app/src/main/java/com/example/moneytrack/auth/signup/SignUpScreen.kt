package com.example.moneytrack.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moneytrack.core.components.AppButton
import com.example.moneytrack.core.components.Screen
import com.example.moneytrack.core.components.textfield.AppTextFieldState
import com.example.moneytrack.core.components.textfield.rememberTextFieldState
import com.example.moneytrack.ui.theme.LocalDimens
import com.example.moneytrack.core.components.textfield.AppTextField

private const val TAG = "SignUpScreen"

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToOtpVerification: (token: String) -> Unit
) {
    val userNameTextFieldState =
        rememberTextFieldState(hint = "Enter your name", initValue = "s")
    val emailTextFieldState =
        rememberTextFieldState(hint = "Enter your email", initValue = "s")
    val passwordTextFieldState = rememberTextFieldState(hint = "Password", initValue = "s")
    val confirmPasswordTextFieldState =
        rememberTextFieldState(hint = "Please re-enter your password", initValue = "s")

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.signUpToken) {
        val token = state.signUpToken
        if (token != null) {
            navigateToOtpVerification(token)
            viewModel.onSignUpSuccessHandled()
        }
    }
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
                        confirmPasswordTextFieldState.equalsText(passwordTextFieldState.text)
                    ) {
                        viewModel.signUp(
                            name = userNameTextFieldState.text,
                            email = emailTextFieldState.text,
                            password = passwordTextFieldState.text
                        )
                    }
                }
            )
            Spacer(modifier = Modifier)
        }
    }
}


@Composable
private fun SignUpTextFields(
    modifier: Modifier = Modifier,
    userNameTextFieldState: AppTextFieldState,
    emailTextFieldState: AppTextFieldState,
    passwordTextFieldState: AppTextFieldState,
    confirmPasswordTextFieldState: AppTextFieldState,
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen64))
        AppTextField(
            title = "Name",
            textFieldState = userNameTextFieldState
        )
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen16))
        AppTextField(
            title = "Email",
            textFieldState = emailTextFieldState
        )
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen16))
        AppTextField(
            title = "Password",
            textFieldState = passwordTextFieldState
        )
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen16))
        AppTextField(
            title = "Confirm Password",
            textFieldState = confirmPasswordTextFieldState
        )
    }
}


@Composable
private fun SignUpTitle(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Create Account", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen4))
        Text(
            text = "Please fill up your details for the account.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SignUpButtonRow(
    modifier: Modifier = Modifier,
    signUp: () -> Unit,
    navigateBack: () -> Unit
) {
    val localKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                signUp()
                localKeyboardController?.hide()
            },
            text = "Sign Up"
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Have an Account?", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(LocalDimens.current.dimen4))
            Text(
                text = "Sign In now",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = navigateBack)
            )
        }
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen24))
    }
}