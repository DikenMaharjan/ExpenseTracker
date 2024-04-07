package com.example.moneytrack.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.data.model.Category
import com.example.data.model.fakeCategory
import com.example.moneytrack.ui.theme.MoneyTrackTheme
import kotlin.math.abs

@Composable
fun CategoryView(
    modifier: Modifier = Modifier,
    category: Category
) {
    Box(modifier = modifier.background(color = category.hashCode().getBackgroundColor())) {
        CompositionLocalProvider(LocalContentColor provides category.hashCode().getContentColor()) {
            Text(text = category.name)
        }
    }
}

@Composable
fun Int.getContentColor(): Color {
    return remember(this) {
        Color.hsl(
            hue = this.hue(),
            saturation = 1f,
            lightness = 0.6f
        )
    }
}

@Composable
fun Int.getBackgroundColor(): Color {
    return remember(this) {
        Color.hsl(
            hue = this.hue(),
            saturation = 0.4f,
            lightness = 0.95f
        )
    }
}

private fun Int.hue() = (abs(this.hashCode()) % 360).toFloat()


@Preview
@Composable
private fun CategoryViewPreview() {
    MoneyTrackTheme {
        CategoryView(category = fakeCategory("Category 1"))
    }
}