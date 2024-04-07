package com.example.moneytrack.home.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moneytrack.core.components.Screen

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardScreenViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit,
    navigateToAddExpenseBottomSheet: () -> Unit
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    Screen(modifier = modifier.fillMaxSize()) {
        Text(text = authState.toString())
        FloatingActionButton(onClick = navigateToAddExpenseBottomSheet) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "Add Expense"
            )

        }
    }
}
