package com.example.moneytrack.home.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.data.model.AppUser
import com.example.moneytrack.core.components.AppUserIcon
import com.example.moneytrack.ui.theme.LocalDimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(
    modifier: Modifier = Modifier,
    navigateToProfile: () -> Unit,
    appUser: AppUser
) {
    Surface(
        modifier = modifier,
        shadowElevation = LocalDimens.current.dimen8
    ) {
        TopAppBar(
            title = { Text(text = "Dashboard") },
            actions = {
                AppUserIcon(
                    modifier = Modifier
                        .padding(LocalDimens.current.dimen12)
                        .clip(CircleShape)
                        .clickable(onClick = navigateToProfile),
                    user = appUser,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        )
    }
}