package com.jarroyo.feature.launches.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jarroyo.feature.launches.sqldelight.Database
import com.jarroyo.feature.launches.sqldelight.DatabaseWrapper
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun launchesModule() = module {
    single {
        val driver = NativeSqliteDriver(Database.Schema, "Rockets.db")
        DatabaseWrapper(Database(driver))
    }
    single { Darwin.create() }
}
