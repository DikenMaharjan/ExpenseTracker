package com.example.moneytrack.core.components

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.moneytrack.core.model.ScreenState

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    screenState: ScreenState = ScreenState(),
    showBack: Boolean = false,
    title: String? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .systemBarsPadding(),
            verticalArrangement = Arrangement.Top
        ) {
            if (showBack || title != null) {
                ScreenTopBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    showBack = showBack
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                content = content
            )
        }
        if (screenState.isLoading) {
            TouchConsumingLoading(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        val errorMsg = screenState.errorMsg
        if (errorMsg != null) {
            ErrorDialog(
                onDismiss = screenState.clearError,
                msg = errorMsg
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenTopBar(
    modifier: Modifier = Modifier,
    title: String?,
    showBack: Boolean
) {
    val localBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current

    TopAppBar(
        modifier = modifier,
        title = {
            if (title != null) {
                Text(text = title)
            }
        },
        navigationIcon = {
            if (showBack) {
                IconButton(
                    onClick = {
                        localBackPressedDispatcher?.onBackPressedDispatcher?.onBackPressed()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back Icon"
                    )
                }
            }
        },
        windowInsets = rememberZeroWindowInsets()
    )
}


@Composable
fun rememberZeroWindowInsets(): WindowInsets {
    return remember {
        WindowInsets(0, 0, 0, 0)
    }
}


@Composable
private fun TouchConsumingLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.4f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {}
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorDialog(
    onDismiss: () -> Unit,
    msg: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            Text(text = "Dismiss", modifier = Modifier.clickable(onClick = onDismiss))
        }, title = {
            Text(text = "Error")
        }, text = {
            Text(text = msg)
        }
    )
}