package com.example.moneytrack.home.add_expense

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.category.CategoryRepository
import com.example.data.expense.ExpenseRepository
import com.example.utils.extensions.stateInWhileSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AddExpenseBottomSheetVi"
@HiltViewModel
class AddExpenseBottomSheetViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val categories = categoryRepository
        .categories
        .stateInWhileSubscribed(listOf())

    init {
        viewModelScope.launch {
            categories.collectLatest {
                it.forEach {
                    Log.e(TAG, ": $it", )
                }
            }
        }
    }


}
