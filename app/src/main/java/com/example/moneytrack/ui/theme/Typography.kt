package com.example.moneytrack.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.moneytrack.R

private val UrbanistFamily = FontFamily(
    Font(resId = R.font.urbanist_bold, weight = FontWeight.Bold),
    Font(resId = R.font.urbanist_extrabold, weight = FontWeight.ExtraBold),
    Font(resId = R.font.urbanist_extralight, weight = FontWeight.ExtraLight),
    Font(resId = R.font.urbanist_light, weight = FontWeight.Light),
    Font(resId = R.font.urbanist_medium, weight = FontWeight.Medium),
    Font(resId = R.font.urbanist_regular, weight = FontWeight.Normal),
    Font(resId = R.font.urbanist_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.urbanist_thin, weight = FontWeight.Thin),

    )


private val defaultTypography = Typography()

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = UrbanistFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = UrbanistFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = UrbanistFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = UrbanistFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = UrbanistFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = UrbanistFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = UrbanistFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = UrbanistFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = UrbanistFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = UrbanistFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = UrbanistFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = UrbanistFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = UrbanistFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = UrbanistFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = UrbanistFamily)
)