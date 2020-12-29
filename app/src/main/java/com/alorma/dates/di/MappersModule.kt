package com.alorma.dates.di

import com.alorma.dates.ui.dates.DatesListMapper
import org.koin.dsl.module

object MappersModule {

    operator fun invoke() = module {
        factory { DatesListMapper() }
    }

}