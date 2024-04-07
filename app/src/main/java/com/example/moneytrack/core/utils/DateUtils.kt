package com.example.moneytrack.core.utils

import java.time.LocalDate
import java.time.temporal.ChronoUnit

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
            val days = ChronoUnit.DAYS.between(this, now)
            "$days days ago"
        }
    }
}