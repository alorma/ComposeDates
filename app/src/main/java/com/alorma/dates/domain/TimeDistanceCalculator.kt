package com.alorma.dates.domain

import java.time.*
import java.time.temporal.ChronoField

class TimeDistanceCalculator(private val clock: Clock) {

    private val clockTime: LocalDateTime = LocalDateTime.now(clock)

    fun calculateTimeDistance(localDateTime: LocalDateTime): TimeCalculation {
        val period = Period.between(localDateTime.toLocalDate(), clockTime.toLocalDate()).let {
            if (it.isNegative) {
                it.negated()
            } else {
                it
            }
        }

        val years = period.years
        val months = period.months
        val days = period.days

        val duration = Duration.between(localDateTime.toLocalTime(), clockTime.toLocalTime()).abs()

        val midNightPlus = LocalTime.MIDNIGHT.plus(duration)

        val hours = midNightPlus.get(ChronoField.HOUR_OF_DAY)
        val minutes = midNightPlus.get(ChronoField.MINUTE_OF_HOUR)
        val seconds = midNightPlus.get(ChronoField.SECOND_OF_MINUTE)

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
            abstract val years: Int
            abstract val months: Int
            abstract val days: Int

            abstract val hours: Int
            abstract val minutes: Int
            abstract val seconds: Int

            data class Before(
                override val years: Int,
                override val months: Int,
                override val days: Int,
                override val hours: Int,
                override val minutes: Int,
                override val seconds: Int
            ) : Elapsed()

            data class After(
                override val years: Int,
                override val months: Int,
                override val days: Int,
                override val hours: Int,
                override val minutes: Int,
                override val seconds: Int
            ) : Elapsed()
        }
    }
}