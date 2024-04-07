package com.example.data.expense

import com.example.data.model.Category
import com.example.data.model.Expense
import com.example.data.model.toModel
import com.example.database.datasource.LocalExpenseDataSource
import com.example.database.entity.ExpenseEntity
import com.example.database.relation.ExpenseAndCategory
import com.example.datastore.proto.UserDataDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val localExpenseDataSource: LocalExpenseDataSource,
    private val userDataDataSource: UserDataDataSource,
) {

    val expenses: Flow<List<Expense>> = getExpense { userId ->
        localExpenseDataSource.getAllExpenses(userId)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getExpense(query: (userId: String) -> Flow<List<ExpenseAndCategory>>): Flow<List<Expense>> {
        return userDataDataSource
            .loggedInUserID
            .flatMapLatest { userId ->
                if (userId == null) {
                    flowOf(listOf())
                } else {
                    query(userId)
                }
            }.map { it.map(ExpenseAndCategory::toModel) }
    }

    fun getLastWeekExpenses(): Flow<List<Expense>> {
        val today = LocalDateTime.now().plusDays(1)
        val weekAgo = today.minusWeeks(1)
        return getExpenses(from = weekAgo, end = today)
    }


    private fun getExpenses(from: LocalDateTime, end: LocalDateTime): Flow<List<Expense>> {
        return getExpense { userId ->
            localExpenseDataSource.getExpenses(
                userID = userId,
                start = from,
                end = end
            )
        }
    }

    suspend fun insertExpense(
        name: String,
        amount: Double,
        category: Category,
        createdAt: LocalDateTime = LocalDateTime.now()
    ) {
        withContext(NonCancellable) {
            userDataDataSource.withUserID { userId ->
                localExpenseDataSource.storeExpense(
                    ExpenseEntity(
                        createdDate = createdAt,
                        expense = amount,
                        userId = userId,
                        name = name,
                        categoryId = category.id
                    )
                )
            }
        }
    }


}

