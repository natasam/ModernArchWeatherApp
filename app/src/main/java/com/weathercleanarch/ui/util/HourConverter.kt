package com.weathercleanarch.ui.util
object HourConverter {
    fun convertHour(hour: String): String {
        val hourInt = hour.toIntOrNull() ?: throw IllegalArgumentException("Invalid hour format: $hour")

        return when (hourInt) {
            in 1..11 -> "$hour AM"
            0 -> "12 AM"
            12 -> "12 PM"
            in 13..23 -> "${hourInt - 12} PM"
            else -> throw IllegalArgumentException("Hour out of range: $hour")
        }
    }
}