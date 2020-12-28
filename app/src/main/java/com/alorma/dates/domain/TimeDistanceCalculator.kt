package com.alorma.dates.domain

import java.time.Clock
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period

class TimeDistanceCalculator(private val clock: Clock) {

    private val clockTime: LocalDateTime = LocalDateTime.now(clock)

    fun calculateTimeDistance(localDateTime: LocalDateTime): TimeCalculation {
        var period = Period.between(localDateTime.toLocalDate(), clockTime.toLocalDate())
        var duration = Duration.between(localDateTime.toLocalTime(), clockTime.toLocalTime())

        val years = period.years.toLong()
        period = period.minusYears(years)
        val months = period.months.toLong()
        period = period.minusMonths(months)
        val days = period.days.toLong()

        val hours = duration.toHours()
        duration = duration.minusHours(hours)
        val minutes = duration.toMinutes()
        duration = duration.minusMinutes(minutes)
        val seconds = duration.seconds

        return when {
            localDateTime.isEqual(clockTime) -> {
                TimeCalculation.Now
            }
            localDateTime.isAfter(clockTime) -> {
                TimeCalculation.Elapsed.After(years, months, days, hours, minutes, seconds)
            }
            localDateTime.isBefore(clockTime) -> {
                TimeCalculation.Elapsed.Before(years, months, days, hours, minutes, seconds)
            }
            else -> error("Invalid time calculation")
        }
    }

    sealed class TimeCalculation {
        object Now : TimeCalculation()
        sealed class Elapsed : TimeCalculation() {
            abstract val years: Long
            abstract val months: Long
            abstract val days: Long

            abstract val hours: Long
            abstract val minutes: Long
            abstract val seconds: Long

            data class Before(
                override val years: Long,
                override val months: Long,
                override val days: Long,
                override val hours: Long,
                override val minutes: Long,
                override val seconds: Long
            ) : Elapsed()

            data class After(
                override val years: Long,
                override val months: Long,
                override val days: Long,
                override val hours: Long,
                override val minutes: Long,
                override val seconds: Long
            ) : Elapsed()
        }
    }
}