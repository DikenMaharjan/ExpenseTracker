package com.example.database.datasource

import com.example.database.dao.ExpenseDao
import com.example.database.entity.ExpenseEntity
import com.example.database.relation.ExpenseAndCategory
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalExpenseDataSource @Inject constructor(
    private val localExpenseDao: ExpenseDao
) {
    fun getAllExpenses(userID: String): Flow<List<ExpenseAndCategory>> {
        return localExpenseDao.getAllExpenses(userID)
    }

    fun getExpenses(
        userID: String,
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<ExpenseAndCategory>> {
        return localExpenseDao.getExpenses(
            userId = userID,
            start = start,
            end = end
        )
    }

    suspend fun storeExpense(expenseEntity: ExpenseEntity) {
        localExpenseDao.upsert(expenseEntity)
    }
}