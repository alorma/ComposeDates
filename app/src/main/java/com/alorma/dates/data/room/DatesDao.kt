package com.alorma.dates.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface DatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: DateEntity)

    @Query("SELECT * FROM dates ORDER BY date ASC")
    fun getAll(): Flow<List<DateEntity>>

    @Query("SELECT * FROM dates WHERE id=:id")
    fun getDate(id: Long): Flow<DateEntity>

    fun getDateUntilChanged(id: Long): Flow<DateEntity> =
        getDate(id).distinctUntilChanged()
}