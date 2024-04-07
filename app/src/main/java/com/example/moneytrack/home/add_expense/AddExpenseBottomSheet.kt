package com.example.moneytrack.home.add_expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.moneytrack.ui.theme.LocalDimens

@Composable
fun AddExpenseBottomSheet(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalDimens.current.dimen200)
            .background(Color.Red)
    ) {

    }

}