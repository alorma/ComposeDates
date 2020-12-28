package com.alorma.dates.dates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alorma.dates.R
import org.koin.androidx.compose.getViewModel

@Composable
fun DateListScreen(datesListViewModel: DatesListViewModel = getViewModel()) {
    val state = datesListViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (val current = state.value) {
                        DatesState.Loading -> Text(text = stringResource(id = R.string.app_name))
                        is DatesState.Loaded -> Text(text = current.currentTime)
                    }
                }
            )
        }
    ) {

        when (val actualState = state.value) {
            DatesState.Loading -> DatesLoading()
            is DatesState.Loaded -> DatesLoaded(actualState)
        }
    }
}

@Composable
fun DatesLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun DatesLoaded(state: DatesState.Loaded) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(items = state.dates, itemContent = { item ->
            Text(text = item)
        })
    }
}