package com.example.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.database.entity.CategoryEntity
import com.example.database.entity.ExpenseEntity

data class ExpenseAndCategory(
    @Embedded val expense: ExpenseEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity
)