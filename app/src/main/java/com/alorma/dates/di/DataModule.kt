package com.alorma.dates.di

import androidx.room.Room
import com.alorma.dates.data.room.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DataModule {

    operator fun invoke() = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                "database-name"
            ).build()
        }

        single { get<AppDatabase>().datesDao() }
    }
}