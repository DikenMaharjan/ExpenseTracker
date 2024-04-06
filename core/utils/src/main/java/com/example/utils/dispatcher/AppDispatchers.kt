package com.example.utils.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(
    val default: CoroutineDispatcher,
    val background: CoroutineDispatcher,
)