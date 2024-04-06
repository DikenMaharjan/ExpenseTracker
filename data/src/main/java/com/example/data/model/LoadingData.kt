package com.example.data.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

// Data that needs to be loaded up.

sealed class LoadingData<out T> {
    data class SuccessData<T>(
        val data: T
    ) : LoadingData<T>()

    data object Loading : LoadingData<Nothing>()

    class Error(val exception: Throwable) : LoadingData<Nothing>() {
        val errorMsg = exception.errorMsg
    }
}

fun <T> T.toSuccessData() = LoadingData.SuccessData(this)

fun Exception.toLoadingError() = LoadingData.Error(this)
fun String.toLoadingError() = Exception(this).toLoadingError()

fun <T> Result<T>.toLoadingData(): LoadingData<T> = fold(
    onSuccess = {
        it.toSuccessData()
    },
    onFailure = {
        LoadingData.Error(it)
    }
)

@OptIn(ExperimentalContracts::class)
fun <T> LoadingData<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is LoadingData.SuccessData)
    }
    return this is LoadingData.SuccessData
}


val Throwable.errorMsg: String
    get() = message ?: SOMETHING_WENT_WRONG

const val SOMETHING_WENT_WRONG = "Something went wrong."