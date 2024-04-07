package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class ExpenseEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val createdDate: LocalDateTime,
    val expense: Double,
    val userId: String,
    val name: String,
    val categoryId: String
)