package com.example.moneytrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.moneytrack.core.components.rememberHalfExpandedSkippingBottomSheetNavigator
import com.example.moneytrack.navigation.ROOT
import com.example.moneytrack.navigation.appGraph
import com.example.moneytrack.ui.theme.MoneyTrackTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkModeEnabled by viewModel.isDarkMode.collectAsStateWithLifecycle()
            MoneyTrackTheme(
                darkTheme = isDarkModeEnabled
            ) {
                AppContent(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun AppContent(
    modifier: Modifier = Modifier
) {
    val bottomSheetNavigator = rememberHalfExpandedSkippingBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val viewModel = hiltViewModel<MainActivityViewModel>()
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = MaterialTheme.shapes.large
    ) {
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
}