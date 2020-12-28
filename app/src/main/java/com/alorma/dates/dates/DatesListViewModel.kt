package com.alorma.dates.dates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DatesListViewModel(
    private val clock: Clock,
    private val datesListMapper: DatesListMapper
) : ViewModel() {

    private val _state: MutableStateFlow<DatesState> = MutableStateFlow(
        DatesState.Loading
    )
    val state: StateFlow<DatesState> = _state

    init {
        viewModelScope.launch {
            delay(3_000)
            while (true) {
                val instant = clock.instant()
                val dates = loadDatesFromTime(instant)
                _state.emit(
                    datesListMapper.map(instant, dates)
                )
                delay(5_000)
            }
        }
    }

    private suspend fun loadDatesFromTime(instant: Instant): List<LocalDateTime> =
        (0..(1..15).random()).map { num ->
            LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).plusDays(num.toLong())
        }

}