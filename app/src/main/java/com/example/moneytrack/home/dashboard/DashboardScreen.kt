package com.example.moneytrack.home.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.auth.AuthRepository
import com.example.data.model.AppUser
import com.example.data.model.Expense
import com.example.moneytrack.core.components.AppUserIcon
import com.example.moneytrack.home.dashboard.components.GroupedExpenseCard
import com.example.moneytrack.ui.theme.LocalDimens
import java.time.LocalDate

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardScreenViewModel = hiltViewModel(),
    navigateToAuth: () -> Unit,
    navigateToAddExpenseBottomSheet: () -> Unit,
    navigateToProfile: () -> Unit
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val expensesMap by viewModel.groupedExpenses.collectAsStateWithLifecycle()
    Box(modifier = modifier.fillMaxSize()) {
        when (val auth = authState) {
            is AuthRepository.AuthState.LoggedIn -> {
                DashboardScreenContent(
                    navigateToAddExpenseBottomSheet = navigateToAddExpenseBottomSheet,
                    appUser = auth.appUser,
                    expensesMap = expensesMap,
                    navigateToProfile = navigateToProfile
                )
            }

            AuthRepository.AuthState.LoggedOut -> {}
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenContent(
    modifier: Modifier = Modifier,
    navigateToAddExpenseBottomSheet: () -> Unit,
    appUser: AppUser,
    expensesMap: Map<LocalDate, List<Expense>>,
    navigateToProfile: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAddExpenseBottomSheet) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Expense")
            }
        },
        topBar = {
            Surface(
                shadowElevation = LocalDimens.current.dimen8
            ) {
                TopAppBar(
                    title = { Text(text = "All Entries") },
                    actions = {
                        AppUserIcon(
                            modifier = Modifier
                                .padding(LocalDimens.current.dimen8)
                                .clickable(onClick = navigateToProfile),
                            user = appUser,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(LocalDimens.current.dimen24)
        ) {
            item {
                Spacer(modifier = Modifier.height(LocalDimens.current.dimen12))
            }
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