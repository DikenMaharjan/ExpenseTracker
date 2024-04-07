package com.example.utils.extensions

fun <T> Result<T>.toUnitResult() = this.map { Unit }