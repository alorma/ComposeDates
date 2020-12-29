package com.alorma.dates.ui.dates

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.time.temporal.Temporal

class DatesListMapper {

    fun map(instant: Temporal, dates: List<Temporal>): DatesState = DatesState.Loaded(
        currentTime = mapTime(instant = instant),
        dates = mapDates(dates)
    )

    private fun mapTime(instant: Temporal): String {
        val time = LocalDateTime.ofInstant(Instant.from(instant), ZoneId.systemDefault())

        val formatter = DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendLiteral('/')
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendLiteral('/')
            .appendValue(ChronoField.YEAR, 4)
            .appendLiteral(" ")
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .toFormatter()

        return formatter.format(time)
    }

    private fun mapDates(dates: List<Temporal>) =
        dates.map { temporal -> mapDate(temporal) }

    private fun mapDate(localDate: Temporal): String =
        DateTimeFormatter.ISO_LOCAL_DATE.format(localDate)

}
