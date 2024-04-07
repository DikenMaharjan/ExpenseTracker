package com.example.moneytrack.auth.otp.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.example.moneytrack.auth.otp.OTPVerificationScreenViewModel
import com.example.moneytrack.ui.theme.LocalDimens

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    otpCode: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    otpLength: Int = OTPVerificationScreenViewModel.OTP_LENGTH
) {
    val otpInteractionSource = remember {
        MutableInteractionSource()
    }
    val focused by otpInteractionSource.collectIsFocusedAsState()
    BasicTextField(
        modifier = modifier,
        value = otpCode,
        onValueChange = onValueChange,
        singleLine = true,
        interactionSource = otpInteractionSource
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(otpLength) { index ->
                val value = otpCode.getOrNull(index) ?: ""
                val isCursorPlaced = index <= otpCode.length
                Column(
                    modifier = Modifier
                        .border(
                            width = if (focused && isCursorPlaced) {
                                LocalDimens.current.dimen4
                            } else {
                                LocalDimens.current.dimen2
                            },
                            shape = MaterialTheme.shapes.small,
                            color = if (isError) {
                                MaterialTheme.colorScheme.error
                            } else if (focused && isCursorPlaced) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outline
                            }
                        )
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(placeable.height, placeable.height) {
                                placeable.place(
                                    x = (placeable.height - placeable.width) / 2,
                                    y = 0
                                )
                            }
                        }
                        .padding(LocalDimens.current.dimen8),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}