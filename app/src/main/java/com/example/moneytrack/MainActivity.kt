package com.example.moneytrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.moneytrack.navigation.ROOT
import com.example.moneytrack.navigation.appGraph
import com.example.moneytrack.ui.theme.MoneyTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MoneyTrackTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<MainActivityViewModel>()
    NavHost(
        navController = navController,
        modifier = modifier.fillMaxSize(),
        startDestination = ROOT
    ) {
        appGraph(
            navController = navController,
            viewModel = viewModel
        )
    }
}