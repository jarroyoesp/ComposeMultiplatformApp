package com.jarroyo.feature.launches.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jarroyo.feature.launches.sqldelight.Database
import com.jarroyo.feature.launches.sqldelight.DatabaseWrapper
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

actual fun launchesModule() = module {
    single {
        val driver = AndroidSqliteDriver(Database.Schema, get(), "Rockets.db")
        DatabaseWrapper(Database(driver))
    }
    single { Android.create() }
}
