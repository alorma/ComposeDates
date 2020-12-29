package com.alorma.dates.ui.dates

sealed class DatesState {
    object Loading : DatesState()
    data class Loaded(val currentTime: String, val dates: List<String>): DatesState()
}