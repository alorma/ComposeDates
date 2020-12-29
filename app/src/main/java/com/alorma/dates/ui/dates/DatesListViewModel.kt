package com.alorma.dates.ui.dates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.dates.data.room.DateEntity
import com.alorma.dates.data.room.DatesDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

class DatesListViewModel(
    private val datesListMapper: DatesListMapper,
    private val datesDao: DatesDao,
) : ViewModel() {

    private val _state: MutableStateFlow<DatesState> = MutableStateFlow(
        DatesState.Loading
    )
    val state: StateFlow<DatesState> = _state

    init {
        viewModelScope.launch {
            while (true) {
                val dates = datesDao.getAll()
                _state.emit(
                    datesListMapper.map(dates)
                )
                delay(5_000)
            }
        }
    }

    fun add(date: LocalDate) {
        viewModelScope.launch {
            val offsetDateTime = OffsetDateTime.of(date, LocalTime.NOON, ZoneOffset.UTC)
            val entity = DateEntity(
                date = offsetDateTime,
                title = "Date #${(0..100).random()}"
            )
            datesDao.insert(entity)
        }
    }

}