package com.alorma.dates.ui.dates

sealed class DatesState {
    object Loading : DatesState()
    data class Loaded(val dates: List<DateItem>) : DatesState()
}

data class DateItem(val title: String, val date: String)