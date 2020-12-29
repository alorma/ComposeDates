package com.alorma.dates.ui.dates

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alorma.dates.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.datepicker
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate

@Composable
fun DateListScreen(datesListViewModel: DatesListViewModel = getViewModel()) {
    val state = datesListViewModel.state.collectAsState()

    when (val actualState = state.value) {
        DatesState.Loading -> DatesLoading()
        is DatesState.Loaded -> DatesLoaded(actualState) { date ->
            datesListViewModel.add(date)
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
fun DatesLoaded(
    state: DatesState.Loaded,
    onDateSelected: (LocalDate) -> Unit,
) {
    val dateDialog = MaterialDialog()

    dateDialog.build {
        datepicker { date -> onDateSelected(date) }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                dateDialog.show()
            }) {
                Image(imageVector = Icons.Filled.Add)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items = state.dates, itemContent = { item ->
                Column {
                    Text(text = item.title)
                    Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                        Text(text = item.date)
                    }
                }
            })
        }
    }
}