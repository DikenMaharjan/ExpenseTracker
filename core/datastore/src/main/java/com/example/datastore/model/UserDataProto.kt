package com.example.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDataProto(
    val name: String,
    val id: String
)