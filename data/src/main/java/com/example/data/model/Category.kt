package com.example.data.model

import com.example.database.entity.CategoryEntity
import java.util.UUID

data class Category(
    val name: String,
    val id: String
)

fun fakeCategory(name: String) = Category(
    id = UUID.randomUUID().toString(),
    name = name
)

fun CategoryEntity.toModel() = Category(
    name = this.name,
    id = this.id
)