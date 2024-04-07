package com.example.moneytrack.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.auth.AuthRepository
import com.example.data.expense.ExpenseRepository
import com.example.utils.extensions.stateInWhileSubscribed
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.lineSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

private const val TAG = "HomeScreenViewModel"

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val expenses = expenseRepository.expenses

    val groupedExpenses = expenses.map { expenses ->
        expenses.groupBy { it.createdDate.toLocalDate() }
    }.stateInWhileSubscribed(mapOf())

    val orderedDays = kotlin.run {
        val daysOfWeek = DayOfWeek.entries
        val today = LocalDate.now().dayOfWeek

        val currentIndex = daysOfWeek.indexOf(today)

        (currentIndex + 1..(currentIndex + 1 + 7)).map {
            daysOfWeek[it % 7]
        }
    }

    private val weekExpenses = expenseRepository.getLastWeekExpenses().map { it ->
        val grouping = it.groupBy { it.createdDate.dayOfWeek }
            .mapValues { expensesMap ->
                expensesMap.value.sumOf { it.amount }
            }
        listOf(0.0) + orderedDays.map { day ->
            grouping.getOrDefault(day, 0.0)
        } + 0.0
    }.stateInWhileSubscribed(listOf())

    val weekGraphProducer = CartesianChartModelProducer.build()

    init {
        viewModelScope.launch {
            weekExpenses.collectLatest { expenses ->
                if (expenses.isNotEmpty()) {
                    weekGraphProducer.runTransaction {
                        lineSeries {
                            this.series(expenses)
                        }
                    }.await()
                }
            }
        }
    }

    val authState = authRepository.authState

}