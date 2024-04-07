package com.example.moneytrack.core.utils

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import kotlin.math.max

class TextPainter(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    text: String,
    private val backgroundColor: Color
) : Painter() {

    private val textLayoutResult = textMeasurer.measure(
        text = text, textStyle
    )
    private val maxDimension = max(
        textLayoutResult.size.width,
        textLayoutResult.size.height
    ).toFloat()

    override val intrinsicSize: Size = Size(
        maxDimension, maxDimension
    )

    override fun DrawScope.onDraw() {
        drawCircle(backgroundColor, size.maxDimension / 2)
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                x = (this.size.width - textLayoutResult.size.width) / 2,
                y = (this.size.height - textLayoutResult.size.height) / 2
            )
        )
    }
}

@Composable
fun rememberTextPainter(
    text: String,
    backgroundColor: Color,
    style: TextStyle = LocalTextStyle.current,
): Painter {
    val textMeasurer = rememberTextMeasurer()
    val contentColor = backgroundColor.getContentColor()
    val mergedStyle = style.merge(color = contentColor)
    return remember(textMeasurer, text, mergedStyle, backgroundColor) {
        TextPainter(
            text = text,
            textMeasurer = textMeasurer,
            textStyle = mergedStyle,
            backgroundColor = backgroundColor
        )
    }
}

@Composable
fun Color.getContentColor(): Color {
    return MaterialTheme.colorScheme.contentColorFor(this).takeIf {
        it != Color.Unspecified
    } ?: run {
        val isDarkColor = isDarkColor()
        remember(this, isDarkColor) {
            if (isDarkColor) {
                Color.White
            } else {
                Color.Black
            }
        }
    }
}

fun Color.isDarkColor(): Boolean {
    val (r, g, b) = this
    val luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b
    return luminance < 0.6
}

fun getBackgroundColor(key: String): Color {
    return Color.hsl(
        hue = ((key.hashCode() % 360 + 360) % 360).toFloat(),
        saturation = 0.8f,
        lightness = 0.35f
    )
}