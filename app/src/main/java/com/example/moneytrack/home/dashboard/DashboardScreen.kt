package com.example.moneytrack.home.dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardScreenViewModel = hiltViewModel()
) {
    val appUser by viewModel.authState.collectAsStateWithLifecycle()

    Text(text = appUser.toString())
}