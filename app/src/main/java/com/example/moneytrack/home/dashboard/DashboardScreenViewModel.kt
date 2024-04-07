package com.example.moneytrack.home.dashboard

import androidx.lifecycle.ViewModel
import com.example.data.auth.AuthRepository
import com.example.data.expense.ExpenseRepository
import com.example.utils.extensions.stateInWhileSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "HomeScreenViewModel"

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    val expenses = expenseRepository.expenses

    val groupedExpenses = expenses.map { expenses ->
        expenses.groupBy { it.createdDate.toLocalDate() }
    }.stateInWhileSubscribed(mapOf())

    val authState = authRepository.authState

}