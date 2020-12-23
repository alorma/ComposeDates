package com.alorma.dates

import android.app.Application
import com.alorma.dates.di.DatesModule
import com.alorma.dates.di.MappersModule
import com.alorma.dates.di.ViewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DatesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DatesApplication)
            modules(
                listOf(
                    DatesModule(),
                    MappersModule(),
                    ViewModelsModule()
                )
            )
        }
    }

}