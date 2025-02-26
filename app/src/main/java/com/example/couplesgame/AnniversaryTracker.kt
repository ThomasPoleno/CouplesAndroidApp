package com.example.couplesgame

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class AnniversaryTracker(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("AnniversaryPrefs", Context.MODE_PRIVATE)

    fun setAnniversaryFromCalendar(calendar: Calendar) {
        setAnniversary(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // Default anniversary date (July 29, 2024)
    fun setAnniversary(year: Int, month: Int, day: Int) {
        sharedPreferences.edit()
            .putInt("anniversary_year", year)
            .putInt("anniversary_month", month)
            .putInt("anniversary_day", day)
            .apply()
    }

    fun getAnniversary(): Calendar {
        val year = sharedPreferences.getInt("anniversary_year", 2024)
        val month = sharedPreferences.getInt("anniversary_month", Calendar.JULY)
        val day = sharedPreferences.getInt("anniversary_day", 29)

        return Calendar.getInstance().apply {
            set(year, month, day)
        }
    }

    fun getTimeSinceAnniversary(): String {
        val today = Calendar.getInstance()
        val anniversary = getAnniversary()

        // Calculate the difference in years, months, and days correctly
        var years = today.get(Calendar.YEAR) - anniversary.get(Calendar.YEAR)
        var months = today.get(Calendar.MONTH) - anniversary.get(Calendar.MONTH)
        var days = today.get(Calendar.DAY_OF_MONTH) - anniversary.get(Calendar.DAY_OF_MONTH)

        // Adjust if the current day is before the anniversary day in this month
        if (days < 0) {
            months -= 1
            val previousMonth = (today.clone() as Calendar).apply {
                add(Calendar.MONTH, -1)
            }
            days += previousMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        // Adjust if the current month is before the anniversary month
        if (months < 0) {
            years -= 1
            months += 12
        }

        return if (years == 0 && months == 0) {
            "$days days since your anniversary!"
        } else if (years == 0) {
            "$months months and $days days since your anniversary!"
        } else {
            "$years years, $months months, and $days days since your anniversary!"
        }
    }
}