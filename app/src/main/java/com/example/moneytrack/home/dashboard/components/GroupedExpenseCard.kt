package com.example.moneytrack.home.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.data.model.Expense
import com.example.data.model.fakeExpense
import com.example.moneytrack.core.components.getBackgroundColor
import com.example.moneytrack.core.components.getContentColor
import com.example.moneytrack.core.utils.parseTodayTomorrowOrDay
import com.example.moneytrack.ui.theme.LocalDimens
import com.example.moneytrack.ui.theme.MoneyTrackTheme
import com.example.utils.extensions.currencyFormatWithRsPrefix
import java.time.LocalDate

@Composable
fun GroupedExpenseCard(
    modifier: Modifier = Modifier,
    date: LocalDate,
    expenses: List<Expense>
) {
    Card(
        modifier = modifier.padding(LocalDimens.current.dimen12),
    ) {
        Column(
            modifier = Modifier.padding(LocalDimens.current.dimen12)
        ) {
            Text(
                text = "${date.parseTodayTomorrowOrDay()} ($date)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
            expenses.forEachIndexed { index, expense ->
                ExpenseRow(expense = expense)
                if (index != expenses.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun ExpenseRow(
    modifier: Modifier = Modifier,
    expense: Expense
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = LocalDimens.current.dimen8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = expense.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = expense.category.name,
                modifier = Modifier
                    .background(
                        color = expense.category
                            .hashCode()
                            .getBackgroundColor(),
                        shape = MaterialTheme.shapes.extraSmall
                    )
                    .padding(LocalDimens.current.dimen2),
                color = expense.category.hashCode().getContentColor(),
                style = MaterialTheme.typography.labelSmall
            )
        }
        Text(
            text = expense.amount.currencyFormatWithRsPrefix(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Preview
@Composable
private fun GroupedExpenseCardPreview() {
    MoneyTrackTheme {
        GroupedExpenseCard(
            date = LocalDate.now(), expenses = listOf(
                fakeExpense("Expense 1"),
                fakeExpense("Expense 2"),
                fakeExpense("Expense 3"),
                fakeExpense("Expense 4"),
            )
        )
    }
}