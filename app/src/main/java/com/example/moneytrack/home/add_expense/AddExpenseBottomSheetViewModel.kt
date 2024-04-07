package com.example.moneytrack.home.add_expense

import androidx.lifecycle.ViewModel
import com.example.data.expense.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddExpenseBottomSheetViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {



}
