package com.alorma.dates.domain

import java.time.*
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalField

class TimeDistanceCalculator(private val clock: Clock) {

    private val clockTime: LocalDateTime = LocalDateTime.now(clock)

    fun calculateTimeDistance(localDateTime: LocalDateTime): TimeCalculation {
        var period = Period.between(localDateTime.toLocalDate(), clockTime.toLocalDate()).let {
            if (it.isNegative) {
                it.negated()
            } else {
                it
            }
        }

        val years = period.years.toLong()
        period = period.minusYears(years)
        val months = period.months.toLong()
        period = period.minusMonths(months)
        val days = period.days.toLong()

        val duration = Duration.between(localDateTime.toLocalTime(), clockTime.toLocalTime()).abs()

        val midNightPlus = LocalTime.MIDNIGHT.plus(duration)

        val hours = midNightPlus.get(ChronoField.HOUR_OF_DAY).toLong()
        val minutes = midNightPlus.get(ChronoField.MINUTE_OF_HOUR).toLong()
        val seconds = midNightPlus.get(ChronoField.SECOND_OF_MINUTE).toLong()

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