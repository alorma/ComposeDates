package com.alorma.dates.di

import org.koin.dsl.module
import java.time.Clock

object DatesModule {

    operator fun invoke() = module {
        factory { Clock.systemDefaultZone() }
    }

}