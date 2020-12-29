package com.alorma.dates.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: DateEntity)

    @Query("SELECT * FROM dates")
    suspend fun getAll(): List<DateEntity>
}