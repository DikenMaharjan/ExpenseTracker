package com.example.moneytrack.core.components

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.data.model.AppUser
import com.example.moneytrack.core.utils.getBackgroundColor
import com.example.moneytrack.core.utils.rememberTextPainter

@Composable
fun AppUserIcon(
    modifier: Modifier = Modifier,
    user: AppUser,
    style: TextStyle = MaterialTheme.typography.headlineLarge
) {
    Image(
        modifier = modifier,
        painter = rememberTextPainter(
            text = user.name.uppercase().take(1),
            backgroundColor = getBackgroundColor(user.name),
            style = style
        ),
        contentDescription = null
    )
}
