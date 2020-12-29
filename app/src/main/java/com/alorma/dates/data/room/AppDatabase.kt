package com.alorma.dates.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alorma.dates.data.room.converter.InstantConverter

@Database(
    entities = [DateEntity::class],
    version = 1
)
@TypeConverters(value = [InstantConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun datesDao(): DatesDao
}