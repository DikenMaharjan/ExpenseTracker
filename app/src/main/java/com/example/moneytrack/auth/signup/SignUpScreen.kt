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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moneytrack.core.components.AppButton
import com.example.moneytrack.ui.theme.LocalDimens
import com.example.notesing.core.components.textfield.AppTextField
import com.example.notesing.core.components.textfield.rememberTextFieldState

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val userNameTextFieldState = rememberTextFieldState(hint = "Enter your name")
    val emailTextFieldState = rememberTextFieldState(hint = "Enter your email")
    val passwordTextFieldState = rememberTextFieldState(hint = "Password")
    val confirmPasswordTextFieldState =
        rememberTextFieldState(hint = "Please re-enter your password")
    val localKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Text(text = "Create Account", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(LocalDimens.current.dimen4))
            Text(
                text = "Please fill up your details for the account.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Column {
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
            Spacer(modifier = Modifier.height(LocalDimens.current.dimen48))
        }

        Column {
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
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
                        localKeyboardController?.hide()
                    }
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
        Spacer(modifier = Modifier)
    }
}


