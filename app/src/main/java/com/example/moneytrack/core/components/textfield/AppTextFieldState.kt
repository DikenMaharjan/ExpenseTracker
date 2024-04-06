package com.example.notesing.core.components.textfield

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
    textType: AppTextFieldState.TextType = AppTextFieldState.TextType.Text,
    highlightError: Boolean = true,
    transform: (String) -> String = { it }
): AppTextFieldState =
    rememberSaveable(initValue, saver = AppTextFieldState.Saver) {
        AppTextFieldState(
            initValue = initValue,
            hint = hint,
            textType = textType,
            highlightError = highlightError,
            transform = transform
        )
    }

open class AppTextFieldState(
    val initValue: String,
    val hint: String,
    val textType: TextType,
    val highlightError: Boolean,
    val transform: (String) -> String = { it }
) {
    var text by mutableStateOf(initValue)
        protected set

    var isError by mutableStateOf(false)
        protected set

    fun updateText(value: String) {
        text = transform(value)
        isError = false
    }

    fun equalsText(other: String): Boolean {
        isError = text != other
        return !isError
    }


    val isValid: Boolean
        get() = textType.run {
            isError = when (this) {
                TextType.Text -> text.isBlank()
                TextType.Username -> text.any {
                    !it.isLetter() && !it.isWhitespace()
                } || text.length <= 3

                TextType.Password -> text.isBlank()
            }
            !isError
        }

    enum class TextType(val keyboardType: KeyboardType) {
        Text(KeyboardType.Text),
        Password(KeyboardType.Text),
        Username(KeyboardType.Text)
    }

    companion object {
        val Saver: Saver<AppTextFieldState, *> = listSaver(
            save = { listOf(it.text, it.hint, it.textType.name, it.highlightError) },
            restore = {
                AppTextFieldState(
                    initValue = it[0] as String,
                    hint = it[1] as String,
                    textType = TextType.valueOf(it[2] as String),
                    highlightError = it[3] as Boolean
                )
            }
        )
    }
}