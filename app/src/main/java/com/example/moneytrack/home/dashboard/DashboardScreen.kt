package com.example.moneytrack.home.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moneytrack.core.components.Screen
import com.example.moneytrack.home.dashboard.components.GroupedExpenseCard

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardScreenViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit,
    navigateToAddExpenseBottomSheet: () -> Unit
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val expensesMap by viewModel.groupedExpenses.collectAsStateWithLifecycle()
    Screen(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            items(expensesMap.keys.toList()) { date ->
                val expenses = expensesMap[date]
                if (expenses != null) {
                    GroupedExpenseCard(
                        date = date,
                        expenses = expenses
                    )
                }
            }

        }
    }
}
