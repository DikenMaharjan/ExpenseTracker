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

    @OptIn(ExperimentalCoroutinesApi::class)
    val expenses: Flow<List<Expense>> = userDataDataSource
        .loggedInUserID
        .flatMapLatest { userId ->
            if (userId == null) {
                flowOf(listOf())
            } else {
                localExpenseDataSource.getAllExpenses(userId)
            }
        }.map { it.map(ExpenseAndCategory::toModel) }

    suspend fun insertExpense(
        name: String,
        amount: Double,
        category: Category
    ) {
        withContext(NonCancellable) {
            userDataDataSource.withUserID { userId ->
                localExpenseDataSource.storeExpense(
                    ExpenseEntity(
                        createdDate = LocalDateTime.now(),
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

