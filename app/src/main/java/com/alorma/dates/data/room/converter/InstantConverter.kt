package com.alorma.dates.data.room.converter

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class InstantConverter {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toInstant(instant: String): OffsetDateTime {
        return OffsetDateTime.from(formatter.parse(instant))
    }

    @TypeConverter
    fun toString(instant: OffsetDateTime): String {
        return formatter.format(instant)
    }
}