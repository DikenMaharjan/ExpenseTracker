package com.example.database.datasource

import com.example.database.dao.ExpenseDao
import com.example.database.entity.ExpenseEntity
import com.example.database.relation.ExpenseAndCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalExpenseDataSource @Inject constructor(
    private val localExpenseDao: ExpenseDao
) {
    fun getAllExpenses(userID: String): Flow<List<ExpenseAndCategory>> {
        return localExpenseDao.getAllExpenses(userID)
    }

    suspend fun storeExpense(expenseEntity: ExpenseEntity) {
        localExpenseDao.upsert(expenseEntity)
    }
}