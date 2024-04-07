package com.example.moneytrack.core.components.textfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun rememberTextFieldState(
    hint: String,
    initValue: String = "",
    textType: TextType = TextType.Text,
    transform: (String) -> String = { it }
): AppTextFieldState =
    rememberSaveable(initValue, saver = AppTextFieldState.Saver) {
        AppTextFieldState(
            initValue = initValue,
            hint = hint,
            textType = textType,
            transform = transform
        )
    }

open class AppTextFieldState(
    val initValue: String,
    val hint: String,
    val textType: TextType,
    val transform: (String) -> String = { it }
) {
    var text by mutableStateOf(initValue)
        protected set

    var errorMsg: String? by mutableStateOf(null)
        protected set

    fun updateText(value: String) {
        text = transform(value)
        errorMsg = null
    }

    fun equalsText(other: String, getErrorMsg: () -> String): Boolean {
        return (text == other).also { isEqual ->
            if (!isEqual) {
                errorMsg = getErrorMsg()
            }
        }
    }


    val isValid: Boolean
        get() = run {
            errorMsg = textType.rule.getError(text)
            errorMsg == null
        }

    val isValidSilent: Boolean
        get() = textType.rule.getError(text) == null

    companion object {
        val Saver: Saver<AppTextFieldState, *> = listSaver(
            save = { listOf(it.text, it.hint, it.textType.name) },
            restore = {
                AppTextFieldState(
                    initValue = it[0] as String,
                    hint = it[1] as String,
                    textType = TextType.valueOf(it[2] as String),
                )
            }
        )
    }
}

enum class TextType(val keyboardType: KeyboardType, val rule: Rule) {
    Text(KeyboardType.Text, Rules.requiredRule),
    Password(KeyboardType.Text, Rules.requiredRule),
    Username(KeyboardType.Text, Rules.userNameRule),
    Amount(KeyboardType.Decimal, Rules.amountRule),
    Email(KeyboardType.Email, Rules.emailRule)
}