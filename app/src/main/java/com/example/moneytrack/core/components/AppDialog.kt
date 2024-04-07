package com.example.moneytrack.core.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AppDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    dismissText: String,
    confirmText: String

) {
    AlertDialog(
        title = { Text(text = title) },
        text = {
            Text(text = description)
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissText)
            }
        }
    )

}