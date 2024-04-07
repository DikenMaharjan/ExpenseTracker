package com.example.data.model

import java.time.LocalDateTime

data class Expense(
    val name: String,
    val createdDate: LocalDateTime,
    val id: String,
    val amount: Double
)