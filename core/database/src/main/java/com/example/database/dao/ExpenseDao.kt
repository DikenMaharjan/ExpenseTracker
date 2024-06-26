package com.example.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.database.entity.ExpenseEntity
import com.example.database.relation.ExpenseAndCategory
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ExpenseDao {

    @Transaction
    @Query("Select * from ExpenseEntity where userId = :userId order by createdDate desc")
    fun getAllExpenses(userId: String): Flow<List<ExpenseAndCategory>>

    @Transaction
    @Query("Select * from ExpenseEntity where userId = :userId and createdDate > :start and createdDate < :end  order by createdDate desc")
    fun getExpenses(
        userId: String,
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<ExpenseAndCategory>>

    @Upsert
    suspend fun upsert(expense: ExpenseEntity)


}