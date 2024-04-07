package com.example.moneytrack.home.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.moneytrack.ui.theme.LocalDimens

@Composable
fun ProfileRowOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    title: String,
    description: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(LocalDimens.current.dimen16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(LocalDimens.current.dimen16))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            if (description.isNotBlank()) {
                Spacer(modifier = Modifier.width(LocalDimens.current.dimen4))
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

