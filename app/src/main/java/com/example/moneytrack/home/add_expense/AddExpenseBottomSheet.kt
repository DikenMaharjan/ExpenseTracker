package com.example.moneytrack.home.add_expense

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Category
import com.example.data.model.fakeCategory
import com.example.moneytrack.core.components.AppButton
import com.example.moneytrack.core.components.textfield.AppTextField
import com.example.moneytrack.core.components.textfield.AppTextFieldState
import com.example.moneytrack.core.components.textfield.TextType
import com.example.moneytrack.core.components.textfield.rememberTextFieldState
import com.example.moneytrack.ui.theme.LocalDimens
import com.example.moneytrack.ui.theme.MoneyTrackTheme

@Composable
fun AddExpenseBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: AddExpenseBottomSheetViewModel = hiltViewModel(),
    closeBottomSheet: () -> Unit
) {
    val expenseTextField = rememberTextFieldState(hint = "Expense Name")
    val amountTextField = rememberTextFieldState(hint = "Enter Amount", textType = TextType.Amount)
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddExpenseBottomSheetContent(
        modifier = modifier,
        expenseTextField = expenseTextField,
        amountTextField = amountTextField,
        categories = categories,
        addExpense = viewModel::addExpense,
        state = state,
        selectCategory = viewModel::selectCategory,
        closeBottomSheet = closeBottomSheet
    )

}

@Composable
private fun AddExpenseBottomSheetContent(
    modifier: Modifier = Modifier,
    expenseTextField: AppTextFieldState,
    amountTextField: AppTextFieldState,
    categories: List<Category>,
    addExpense: (name: String, amount: String) -> Unit,
    state: AddExpenseBottomSheetViewModel.State,
    selectCategory: (Category) -> Unit,
    closeBottomSheet: () -> Unit
) {
    val localKeyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(LocalDimens.current.dimen24)
            .navigationBarsPadding()
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.dimen24)
    ) {
        Text(text = "Add Expense", style = MaterialTheme.typography.titleLarge)
        AppTextField(textFieldState = expenseTextField, title = "Expense Name")
        AppTextField(textFieldState = amountTextField, title = "Amount")
        CategoriesRow(
            categories = categories,
            onCategorySelected = selectCategory,
            selectedCategory = state.selectedCategory
        )

        val isEnabled =
            expenseTextField.isValidSilent && amountTextField.isValidSilent && (state.selectedCategory != null)

        AppButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                addExpense(expenseTextField.text, amountTextField.text)
                expenseTextField.updateText("")
                amountTextField.updateText("")
                localKeyboardController?.hide()
                closeBottomSheet()
            },
            text = "Add",
            isEnabled = isEnabled
        )
    }
}


@Composable
private fun CategoriesRow(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    selectedCategory: Category?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.dimen4)
    ) {
        categories.forEach { category ->
            InputChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        text = category.name
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddExpenseBottomSheetContentPreview() {
    MoneyTrackTheme {
        val selectedCategory = fakeCategory("Category 1")
        AddExpenseBottomSheetContent(
            expenseTextField = rememberTextFieldState(hint = "Expense Name"),
            amountTextField = rememberTextFieldState(hint = "Enter Amount"),
            categories = listOf(
                selectedCategory,
                fakeCategory("Category 1"),
                fakeCategory("Category 2"),
                fakeCategory("Category 3"),
                fakeCategory("Category 4"),
            ),
            addExpense = { _, _ -> },
            state = AddExpenseBottomSheetViewModel.State(
                selectedCategory = selectedCategory
            ),
            selectCategory = {},
            closeBottomSheet = {}
        )
    }
}