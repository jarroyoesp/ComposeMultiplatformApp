package com.jarroyo.feature.launches.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.jarroyo.feature.launches.sqldelight.Database
import com.jarroyo.feature.launches.sqldelight.DatabaseWrapper
import org.koin.dsl.module

actual fun launchesModule() = module {
    single {
        val url = "jdbc:sqlite:./database/Rockets.db"
        val driver = JdbcSqliteDriver(url).also { Database.Schema.create(it) }
        DatabaseWrapper(Database(driver))
    }
}
