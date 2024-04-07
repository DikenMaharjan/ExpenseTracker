package com.example.data.model

import com.example.database.relation.ExpenseAndCategory
import java.time.LocalDateTime
import java.util.Random
import java.util.UUID

data class Expense(
    val name: String,
    val createdDate: LocalDateTime,
    val id: String,
    val amount: Double,
    val category: Category
)

fun ExpenseAndCategory.toModel() = Expense(
    name = this.expense.name,
    createdDate = this.expense.createdDate,
    id = this.expense.userId,
    amount = this.expense.expense,
    category = this.category.toModel()
)

fun fakeExpense(name: String) = Expense(
    name = name,
    createdDate = LocalDateTime.now(),
    id = UUID.randomUUID().toString(),
    amount = Random().nextDouble() * 10000,
    category = fakeCategory("Category $name")
)