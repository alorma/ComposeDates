package com.alorma.dates.feature

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.ui.tooling.preview.Preview
import com.alorma.dates.dates.DatesListViewModel
import com.alorma.dates.ui.DatesTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun DateListScreen(datesListViewModel: DatesListViewModel = getViewModel()) {
    val timeState = datesListViewModel.currentTime.collectAsState()

    CurrentTime(timeState.value)
}

@Composable
fun CurrentTime(time: String) {
    Text(text = time)
}

@Composable
@Preview(showBackground = true)
fun CurrentTimePreviewDay() {
    DatesTheme {
        CurrentTime(time = "18:00")
    }
}