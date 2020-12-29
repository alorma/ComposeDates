package com.alorma.dates.ui.dates

import com.alorma.dates.data.room.DateEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.time.temporal.Temporal

class DatesListMapper {

    fun map(dates: List<DateEntity>): DatesState = DatesState.Loaded(
        dates = mapDates(dates)
    )

    private fun mapDates(dates: List<DateEntity>): List<DateItem> = dates.map { dateEntity ->
        DateItem(
            title = dateEntity.title,
            date = mapDate(dateEntity.date),
        )
    }

    fun mapDate(dateTime: OffsetDateTime): String {
        val zoned = dateTime.toLocalDateTime().atZone(ZoneId.systemDefault())
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(zoned)
    }

}
