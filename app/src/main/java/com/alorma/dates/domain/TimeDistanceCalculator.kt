package com.alorma.dates.domain

import java.time.*
import java.time.temporal.ChronoField

class TimeDistanceCalculator(private val clock: Clock) {

    private val clockTime: LocalDateTime = LocalDateTime.now(clock)

    fun calculateTimeDistance(localDateTime: LocalDateTime): TimeCalculation {
        if (localDateTime.isEqual(clockTime)) {
            return TimeCalculation.Now
        }
        val period = getPeriod(localDateTime)

        val years = period.years
        val months = period.months
        val days = period.days

        val midNightPlus = getDuration(localDateTime)

        val hours = midNightPlus.get(ChronoField.HOUR_OF_DAY)
        val minutes = midNightPlus.get(ChronoField.MINUTE_OF_HOUR)
        val seconds = midNightPlus.get(ChronoField.SECOND_OF_MINUTE)

        return when {
            localDateTime.isAfter(clockTime) -> {
                TimeCalculation.Elapsed.After(years, months, days, hours, minutes, seconds)
            }
            localDateTime.isBefore(clockTime) -> {
                TimeCalculation.Elapsed.Before(years, months, days, hours, minutes, seconds)
            }
            else -> error("Invalid time calculation")
        }
    }

    private fun getDuration(localDateTime: LocalDateTime): LocalTime {
        val duration = Duration.between(localDateTime.toLocalTime(), clockTime.toLocalTime()).abs()
        return LocalTime.MIDNIGHT.plus(duration)
    }

    private fun getPeriod(localDateTime: LocalDateTime): Period {
        return Period.between(localDateTime.toLocalDate(), clockTime.toLocalDate()).let {
            if (it.isNegative) {
                it.negated()
            } else {
                it
            }
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