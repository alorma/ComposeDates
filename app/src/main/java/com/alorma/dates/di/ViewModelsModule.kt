package com.alorma.dates.di

import com.alorma.dates.dates.DatesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelsModule {

    operator fun invoke() = module {
        viewModel { DatesListViewModel(get(), get()) }
    }

}