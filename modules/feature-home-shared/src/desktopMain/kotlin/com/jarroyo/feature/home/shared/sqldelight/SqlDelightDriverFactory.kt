package com.jarroyo.feature.home.shared.sqldelight

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.koin.dsl.module
import com.jarroyo.feature.home.shared.di.DatabaseWrapper

actual fun platformModule() = module {
    single {
        val url = "jdbc:sqlite:./database/Rockets.db"
        val driver = JdbcSqliteDriver(url).also { Database.Schema.create(it) }
        DatabaseWrapper(Database(driver))
    }
}
