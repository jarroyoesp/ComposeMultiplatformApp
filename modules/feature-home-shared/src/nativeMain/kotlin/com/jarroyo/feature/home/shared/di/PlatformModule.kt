package com.jarroyo.feature.home.shared.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jarroyo.feature.home.shared.sqldelight.Database
import com.jarroyo.feature.home.shared.sqldelight.DatabaseWrapper
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = NativeSqliteDriver(Database.Schema, "Rockets.db")
        DatabaseWrapper(Database(driver))
    }
    single { Darwin.create() }
}
