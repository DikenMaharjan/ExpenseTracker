package com.example.moneytrack.auth.signin.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.moneytrack.core.components.AppButton
import com.example.moneytrack.core.components.textfield.AppTextField
import com.example.moneytrack.core.components.textfield.AppTextFieldState
import com.example.moneytrack.ui.theme.LocalDimens

@Composable
internal fun SignInTitle(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Sign In", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen16))
        Text(text = "Please sign in to your MoneyTrack account")
    }
}

@Composable
internal fun SignInTextFields(
    modifier: Modifier = Modifier,
    emailTextFieldState: AppTextFieldState,
    passwordTextFieldState: AppTextFieldState
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AppTextField(
            title = "Email",
            textFieldState = emailTextFieldState
        )
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen32))
        AppTextField(
            title = "Password",
            textFieldState = passwordTextFieldState
        )
    }

}

@Composable
internal fun SignInButtonContent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    navigateToSignUp: () -> Unit
) {

    val localKeyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onClick()
                localKeyboardController?.hide()
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
}