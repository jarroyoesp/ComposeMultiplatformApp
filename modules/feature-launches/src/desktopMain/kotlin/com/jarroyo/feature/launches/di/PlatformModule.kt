package com.jarroyo.feature.launches.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.jarroyo.feature.launches.sqldelight.Database
import com.jarroyo.feature.launches.sqldelight.DatabaseWrapper
import org.koin.dsl.module
import java.io.File

actual fun launchesModule() = module {
    single {
        val dbDir = File(System.getProperty("user.home"), ".myAppData")
        dbDir.mkdirs()
        val dbFile = File(dbDir, "Rockets.db")
        val url = "jdbc:sqlite:${dbFile.absolutePath}"
        val driver = JdbcSqliteDriver(url).also { Database.Schema.create(it) }
        DatabaseWrapper(Database(driver))
    }
}
