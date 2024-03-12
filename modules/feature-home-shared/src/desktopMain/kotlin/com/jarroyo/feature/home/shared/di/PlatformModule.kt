package com.jarroyo.feature.home.shared.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.jarroyo.feature.home.shared.sqldelight.Database
import com.jarroyo.feature.home.shared.sqldelight.DatabaseWrapper
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val url = "jdbc:sqlite:./database/Rockets.db"
        val driver = JdbcSqliteDriver(url).also { Database.Schema.create(it) }
        DatabaseWrapper(Database(driver))
    }
}
