package com.example.moneytrack.auth.signup.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.moneytrack.core.components.AppButton
import com.example.moneytrack.core.components.textfield.AppTextField
import com.example.moneytrack.core.components.textfield.AppTextFieldState
import com.example.moneytrack.ui.theme.LocalDimens

@Composable
internal fun SignUpTextFields(
    modifier: Modifier = Modifier,
    userNameTextFieldState: AppTextFieldState,
    emailTextFieldState: AppTextFieldState,
    passwordTextFieldState: AppTextFieldState,
    confirmPasswordTextFieldState: AppTextFieldState,
) {
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var isConfirmPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
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
            textFieldState = passwordTextFieldState,
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isPasswordVisible) "Hide Password" else "Show password"
                    )
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '\u25CF')
        )
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen16))
        AppTextField(
            title = "Confirm Password",
            textFieldState = confirmPasswordTextFieldState,
            trailingIcon = {
                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                    Icon(
                        imageVector = if (isConfirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isConfirmPasswordVisible) "Hide Password" else "Show password"
                    )
                }
            },
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '\u25CF')
        )
    }
}


@Composable
internal fun SignUpTitle(
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
internal fun SignUpButtonRow(
    modifier: Modifier = Modifier,
    signUp: () -> Unit,
    navigateBack: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = signUp,
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