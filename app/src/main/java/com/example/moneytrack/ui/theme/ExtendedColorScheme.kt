package com.example.moneytrack.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColorScheme(
    val green: Color = Color.Green
)

val extendedLight = ExtendedColorScheme()

val extendedDark = ExtendedColorScheme()


val LocalExtendedColorScheme = staticCompositionLocalOf {
    ExtendedColorScheme()
}