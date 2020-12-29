package com.alorma.dates.ui.dates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDate

class DatesListViewModel(
    private val clock: Clock,
    private val datesListMapper: DatesListMapper
) : ViewModel() {

    private val dates: MutableList<LocalDate> = mutableListOf()

    private val _state: MutableStateFlow<DatesState> = MutableStateFlow(
        DatesState.Loading
    )
    val state: StateFlow<DatesState> = _state

    init {
        viewModelScope.launch {
            delay(3_000)
            while (true) {
                val instant = clock.instant()
                _state.emit(
                    datesListMapper.map(instant, dates)
                )
                delay(5_000)
            }
        }
    }

    fun add(date: LocalDate) {
        dates.add(date)
    }

}