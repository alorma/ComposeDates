package com.alorma.dates.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "dates")
data class DateEntity(
    @ColumnInfo(name = "date")
    val date: OffsetDateTime,
    @ColumnInfo(name = "title")
    val title: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}