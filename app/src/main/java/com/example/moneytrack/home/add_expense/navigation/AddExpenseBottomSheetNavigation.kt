package com.example.moneytrack.home.add_expense.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.moneytrack.home.add_expense.AddExpenseBottomSheet
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

private const val ADD_EXPENSE_ROUTE = "AddExpenseRoute"

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.addExpenseBottomSheet(
    closeBottomSheet: () -> Unit
) {
    bottomSheet(ADD_EXPENSE_ROUTE) {
        AddExpenseBottomSheet(
            closeBottomSheet = closeBottomSheet
        )
    }
}

fun NavController.navigateToAddExpenseBottomSheet() {
    navigate(ADD_EXPENSE_ROUTE)
}