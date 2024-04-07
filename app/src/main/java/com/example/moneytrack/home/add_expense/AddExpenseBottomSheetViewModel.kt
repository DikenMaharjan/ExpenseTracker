package com.example.moneytrack.home.add_expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.category.CategoryRepository
import com.example.data.expense.ExpenseRepository
import com.example.data.model.Category
import com.example.utils.extensions.stateInWhileSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AddExpenseBottomSheetVi"

@HiltViewModel
class AddExpenseBottomSheetViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    val categories = categoryRepository
        .categories
        .stateInWhileSubscribed(listOf())


    fun addExpense(name: String, amount: String) {
        viewModelScope.launch {
            val selectedCategory = state.value.selectedCategory
            if (selectedCategory != null) {
                expenseRepository.insertExpense(
                    name = name,
                    amount = amount.toDouble(),
                    category = selectedCategory
                )
                _state.update { it.copy(selectedCategory = null) }
            }
        }
    }


    fun selectCategory(category: Category) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun addCategory(newCategory: String) {
        viewModelScope.launch {
            val createdCategory = categoryRepository.insertCategory(newCategory)
            _state.update {
                it.copy(
                    sheetFunction = SheetFunction.ViewAllCategories,
                    selectedCategory = createdCategory
                )
            }
        }
    }

    fun startViewingAllCategories() {
        _state.update { it.copy(sheetFunction = SheetFunction.ViewAllCategories) }
    }

    fun startAddingCategory() {
        _state.update { it.copy(sheetFunction = SheetFunction.AddCategory) }
    }

    fun startAddingExpense() {
        _state.update { it.copy(sheetFunction = SheetFunction.AddExpense) }
    }

    data class State(
        val selectedCategory: Category? = null,
        val sheetFunction: SheetFunction = SheetFunction.AddExpense
    )

    enum class SheetFunction {
        AddExpense, ViewAllCategories, AddCategory
    }

}
