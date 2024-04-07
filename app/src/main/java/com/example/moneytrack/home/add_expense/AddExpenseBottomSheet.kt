package com.example.moneytrack.home.add_expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Category
import com.example.data.model.fakeCategory
import com.example.moneytrack.core.components.textfield.AppTextField
import com.example.moneytrack.core.components.textfield.AppTextFieldState
import com.example.moneytrack.core.components.textfield.rememberTextFieldState
import com.example.moneytrack.ui.theme.LocalDimens
import com.example.moneytrack.ui.theme.MoneyTrackTheme

@Composable
fun AddExpenseBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: AddExpenseBottomSheetViewModel = hiltViewModel()
) {
    val expenseTextField = rememberTextFieldState(hint = "Expense Name")
    val amountTextField = rememberTextFieldState(hint = "Enter Amount")
    val categories by viewModel.categories.collectAsStateWithLifecycle()

    AddExpenseBottomSheetContent(
        modifier = modifier,
        expenseTextField = expenseTextField,
        amountTextField = amountTextField,
        categories = categories
    )

}

@Composable
private fun AddExpenseBottomSheetContent(
    modifier: Modifier = Modifier,
    expenseTextField: AppTextFieldState,
    amountTextField: AppTextFieldState,
    categories: List<Category>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(LocalDimens.current.dimen24)
            .systemBarsPadding()
            .imePadding()
    ) {
        Text(text = "Add Expense", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen24))
        AppTextField(textFieldState = expenseTextField, title = "Expense Name")
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen24))
        AppTextField(textFieldState = amountTextField, title = "Amount")
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen24))
        CategoriesRow(categories = categories)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoriesRow(
    modifier: Modifier = Modifier,
    categories: List<Category>
) {
    FlowRow(
        modifier = modifier.fillMaxWidth()
    ) {
        categories.forEach {
            Text(text = it.name)
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AddExpenseBottomSheetContentPreview() {
    MoneyTrackTheme {
        AddExpenseBottomSheetContent(
            expenseTextField = rememberTextFieldState(hint = "Expense Name"),
            amountTextField = rememberTextFieldState(hint = "Enter Amount"),
            categories = listOf(
                fakeCategory("Category 1"),
                fakeCategory("Category 2"),
                fakeCategory("Category 3"),
                fakeCategory("Category 4"),
            )
        )
    }
}