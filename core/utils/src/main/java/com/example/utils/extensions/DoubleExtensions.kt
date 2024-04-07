package com.example.utils.extensions

import java.text.NumberFormat
import java.util.Locale

fun Double.currencyFormat(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    numberFormat.maximumFractionDigits = 2
    numberFormat.minimumIntegerDigits = 2
    return numberFormat.format(this)
}

fun Double.currencyFormatWithRsPrefix() = "Rs. ${currencyFormat()}"
