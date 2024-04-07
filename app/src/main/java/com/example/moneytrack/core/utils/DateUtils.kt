package com.example.moneytrack.core.utils

import java.time.LocalDate
import java.time.format.TextStyle

fun LocalDate.parseTodayTomorrowOrDay(): String {
    val now = LocalDate.now()
    return when (this) {
        now -> {
            "Today"
        }

        now.minusDays(1) -> {
            "Yesterday"
        }

        else -> {
            this.dayOfWeek.getDisplayName(TextStyle.FULL, java.util.Locale.getDefault())
        }
    }
}