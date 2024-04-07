package com.example.moneytrack.home.add_expense

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Category
import com.example.data.model.fakeCategory
import com.example.moneytrack.core.components.AppButton
import com.example.moneytrack.core.components.textfield.AppTextFieldState
import com.example.moneytrack.core.components.textfield.OutlinedAppTextField
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
        closeBottomSheet = closeBottomSheet,
        startViewingAllCategories = viewModel::startViewingAllCategories,
        startAddingCategory = viewModel::startAddingCategory,
        addCategory = viewModel::addCategory,
        startAddingExpense = viewModel::startAddingExpense
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
    closeBottomSheet: () -> Unit,
    startViewingAllCategories: () -> Unit,
    startAddingCategory: () -> Unit,
    addCategory: (String) -> Unit,
    startAddingExpense: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(LocalDimens.current.dimen24)
            .navigationBarsPadding()
            .imePadding()
    ) {
        AddExpenseSheetViews(
            categories = categories,
            selectCategory = selectCategory,
            state = state,
            startViewingAllCategories = startViewingAllCategories,
            expenseTextField = expenseTextField,
            amountTextField = amountTextField,
            addExpense = addExpense,
            closeBottomSheet = closeBottomSheet,
            startAddingCategory = startAddingCategory,
            addCategory = addCategory,
            startAddingExpense = startAddingExpense
        )
    }
}

@Composable
private fun AddExpenseSheetViews(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectCategory: (Category) -> Unit,
    state: AddExpenseBottomSheetViewModel.State,
    startViewingAllCategories: () -> Unit,
    expenseTextField: AppTextFieldState,
    amountTextField: AppTextFieldState,
    addExpense: (name: String, amount: String) -> Unit,
    closeBottomSheet: () -> Unit,
    startAddingCategory: () -> Unit,
    addCategory: (String) -> Unit,
    startAddingExpense: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        AddExpenseForm(
            categories = categories,
            selectCategory = selectCategory,
            state = state,
            startViewingAllCategories = startViewingAllCategories,
            expenseTextField = expenseTextField,
            amountTextField = amountTextField,
            addExpense = addExpense,
            closeBottomSheet = closeBottomSheet
        )
        AnimatedVisibility(
            modifier = Modifier.matchParentSize(),
            visible = state.sheetFunction != AddExpenseBottomSheetViewModel.SheetFunction.AddExpense,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { it }
        ) {
            BackHandler(onBack = startAddingExpense)
            EveryCategories(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .matchParentSize(),
                categories = categories,
                selectedCategory = state.selectedCategory,
                onCategorySelected = selectCategory,
                startAddingCategory = startAddingCategory,
                startAddingExpense = startAddingExpense
            )
            AnimatedVisibility(
                modifier = Modifier.matchParentSize(),
                visible = state.sheetFunction == AddExpenseBottomSheetViewModel.SheetFunction.AddCategory,
                enter = slideInHorizontally { it },
                exit = slideOutHorizontally { it }
            ) {
                BackHandler(onBack = startViewingAllCategories)
                AddNewCategoryView(
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colorScheme.background),
                    addCategory = addCategory
                )
            }
        }
    }
}

@Composable
private fun AddNewCategoryView(
    modifier: Modifier = Modifier,
    addCategory: (String) -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.dimen24)
    ) {
        BottomSheetTopBar(title = "Add new category")
        val newCategory = rememberTextFieldState(hint = "Enter new category")
        OutlinedAppTextField(textFieldState = newCategory)
        Spacer(modifier = Modifier.height(LocalDimens.current.dimen12))
        AppButton(
            onClick = {
                if (newCategory.isValid) {
                    addCategory(newCategory.text)
                }
            },
            text = "Add"
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EveryCategories(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit,
    startAddingCategory: () -> Unit,
    startAddingExpense: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.dimen24)
    ) {
        BottomSheetTopBar(
            title = "All categories"
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
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
        Column {
            AppButton(onClick = startAddingCategory, text = "Create new category")
            Spacer(modifier = Modifier.height(LocalDimens.current.dimen8))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(), onClick = startAddingExpense,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(text = "Done")
            }
        }

    }
}

@Composable
private fun AddExpenseForm(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectCategory: (Category) -> Unit,
    state: AddExpenseBottomSheetViewModel.State,
    startViewingAllCategories: () -> Unit,
    expenseTextField: AppTextFieldState,
    amountTextField: AppTextFieldState,
    addExpense: (name: String, amount: String) -> Unit,
    closeBottomSheet: () -> Unit
) {
    val localKeyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.dimen24)
    ) {
        Text(text = "Add Expense", style = MaterialTheme.typography.titleLarge)

        CategoriesRow(
            categories = categories,
            onCategorySelected = selectCategory,
            selectedCategory = state.selectedCategory,
            startViewingAllCategories = startViewingAllCategories
        )

        OutlinedAppTextField(textFieldState = expenseTextField)

        OutlinedAppTextField(textFieldState = amountTextField)

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
    selectedCategory: Category?,
    startViewingAllCategories: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.dimen4)
    ) {
        val toShowCategories = categories.take(4).let {
            if (selectedCategory != null) {
                if (selectedCategory in it) it else (listOf(selectedCategory) + it).dropLast(
                    1
                )
            } else {
                it
            }
        }
        toShowCategories.forEach { category ->
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
        IconButton(onClick = startViewingAllCategories) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Select More category")
        }
    }
}


@Composable
private fun BottomSheetTopBar(
    modifier: Modifier = Modifier,
    title: String
) {
    val localBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.clickable(onClick = {
                localBackPressedDispatcher?.onBackPressedDispatcher?.onBackPressed()

            }),
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back Icon"
        )
        Spacer(modifier = Modifier.width(LocalDimens.current.dimen8))
        Text(text = title, style = MaterialTheme.typography.titleLarge)
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
            closeBottomSheet = {},
            startAddingExpense = {},
            addCategory = {},
            startViewingAllCategories = {},
            startAddingCategory = {}
        )
    }
}