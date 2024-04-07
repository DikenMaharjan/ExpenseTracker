package com.example.moneytrack.core.components.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.example.moneytrack.ui.theme.LocalDimens

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    textFieldState: AppTextFieldState,
    title: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 4,
    showErrorMsgs: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardActions: KeyboardActions? = null,
    trailingIcon: @Composable () -> Unit = {}
) = AppTextField(
    modifier = modifier,
    title = title,
    value = textFieldState.text,
    placeHolder = textFieldState.hint,
    errorMsg = textFieldState.errorMsg,
    onValueChange = textFieldState::updateText,
    enabled = enabled,
    singleLine = singleLine,
    maxLines = maxLines,
    visualTransformation = visualTransformation,
    keyboardOptions = keyboardOptions.copy(keyboardType = textFieldState.textType.keyboardType),
    textStyle = textStyle,
    keyboardActions = keyboardActions,
    showErrorMsgs = showErrorMsgs,
    trailingIcon = trailingIcon
)

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    placeHolder: String,
    enabled: Boolean = true,
    showErrorMsgs: Boolean,
    errorMsg: String?,
    singleLine: Boolean = true,
    maxLines: Int = 4,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardActions: KeyboardActions? = null,
    trailingIcon: @Composable () -> Unit = {}
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        Text(text = title)
        BasicTextField(
            modifier = modifier
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.labelLarge.copy(
                color = LocalContentColor.current
            ).merge(textStyle),
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions ?: KeyboardActions { keyboardController?.hide() },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = {
                Column {
                    Row {
                        Box(
                            modifier = Modifier
                                .padding(vertical = LocalDimens.current.dimen8)
                                .weight(1f)
                        ) {
                            it.invoke()
                            if (value.isEmpty()) {
                                Text(
                                    text = placeHolder,
                                    style = MaterialTheme.typography.labelLarge.merge(textStyle)
                                        .copy(
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.5f
                                            )
                                        )
                                )
                            }
                        }
                        trailingIcon()
                    }
                    HorizontalDivider(
                        color = if (errorMsg != null) MaterialTheme.colorScheme.error else DividerDefaults.color
                    )
                }
            }
        )
        if (showErrorMsgs && errorMsg != null) {
            Text(
                text = errorMsg,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun OutlinedAppTextField(
    modifier: Modifier = Modifier,
    textFieldState: AppTextFieldState
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textFieldState.text,
        onValueChange = textFieldState::updateText,
        label = {
            Text(text = textFieldState.hint)
        },
        placeholder = {
            Text(text = textFieldState.hint)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = textFieldState.textType.keyboardType),
    )
}