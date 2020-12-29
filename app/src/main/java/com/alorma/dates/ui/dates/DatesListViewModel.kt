package com.alorma.dates.ui.dates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.dates.data.room.DateEntity
import com.alorma.dates.data.room.DatesDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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
            datesDao.getAll().collect { dates ->
                _state.emit(
                    datesListMapper.map(dates)
                )
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