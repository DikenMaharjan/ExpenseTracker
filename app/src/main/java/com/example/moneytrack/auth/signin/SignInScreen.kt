package com.example.moneytrack.auth.signin

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moneytrack.core.components.AppButton
import com.example.moneytrack.core.components.textfield.rememberTextFieldState
import com.example.moneytrack.ui.theme.LocalDimens
import com.example.notesing.core.components.textfield.AppTextField

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInScreenViewModel = hiltViewModel(),
    navigateToSignUp: () -> Unit,
    navigateToOTPVerification: () -> Unit
) {
    val emailTextFieldState =
        rememberTextFieldState(hint = "Enter your email")
    val passwordTextFieldState = rememberTextFieldState(hint = "Password")
    val localKeyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .imePadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Sign In", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(LocalDimens.current.dimen16))
            Text(text = "Please sign in to your MoneyTrack account")
            Spacer(modifier = Modifier.height(LocalDimens.current.dimen24))
        }

        Column {
            AppTextField(
                title = "Email",
                textFieldState = emailTextFieldState
            )
            Spacer(modifier = Modifier.height(LocalDimens.current.dimen32))
            AppTextField(
                title = "Password",
                textFieldState = passwordTextFieldState
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Column {
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (emailTextFieldState.isValid && passwordTextFieldState.isValid) {
                        viewModel.signIn(emailTextFieldState.text, passwordTextFieldState.text)
                        localKeyboardController?.hide()
                    }
                },
                text = "Sign In"
            )
            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Join with us.",
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.width(LocalDimens.current.dimen4))
                Text(
                    text = "Create Account",
                    modifier = Modifier.clickable(onClick = navigateToSignUp),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Spacer(modifier = Modifier)
    }
}