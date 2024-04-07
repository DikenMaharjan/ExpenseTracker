package com.example.data.category.usecase

import com.example.data.category.CategoryRepository
import com.example.data.expense.ExpenseRepository
import com.example.utils.dispatcher.AppDispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.random.Random

class AddRandomExpensesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val expenseRepository: ExpenseRepository,
    private val appDispatchers: AppDispatchers
) {

    suspend fun invoke(count: Int = 50) {
        withContext(appDispatchers.background + NonCancellable) {
            val categories = categoryRepository.categories.first()
            (1..count).map {
                async {
                    expenseRepository.insertExpense(
                        name = randomName,
                        amount = Random.nextDouble() * 10000,
                        category = categories.random(),
                        createdAt = randomDate()
                    )
                }
            }.awaitAll()
        }
    }

    private fun randomDate(): LocalDateTime {
        val end = LocalDateTime.now()
        val start = end.minusMonths(6)
        val days = ChronoUnit.DAYS.between(start, end);
        return start.plusDays(Random.nextLong(days + 1));
    }


    private val randomName: String
        get() = (1..Random.nextInt(10)).joinToString(
            separator = "",
            transform = { ('A'..'Z').random().uppercase() }
        )

}