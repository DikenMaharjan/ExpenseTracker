package com.example.data.model

import java.util.UUID

data class Category(
    val name: String,
    val id: String
)

fun fakeCategory(name: String) = Category(
    id = UUID.randomUUID().toString(),
    name = name
)