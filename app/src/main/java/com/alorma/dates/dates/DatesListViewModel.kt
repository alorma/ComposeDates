package com.alorma.dates.dates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Clock

class DatesListViewModel(
    private val clock: Clock,
    private val datesListMapper: DatesListMapper
) : ViewModel() {

    private val _time: MutableStateFlow<String> =
        MutableStateFlow(datesListMapper.mapTime(clock.instant()))
    val currentTime: StateFlow<String> = _time

    init {
        viewModelScope.launch {
            repeat(10) {
                delay(1_000)
                _time.emit(datesListMapper.mapTime(clock.instant()))
            }
        }
    }

}