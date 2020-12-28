package com.alorma.dates.dates

sealed class DatesState {
    object Loading : DatesState()
    data class Loaded(val currentTime: String, val dates: List<String>): DatesState()
}