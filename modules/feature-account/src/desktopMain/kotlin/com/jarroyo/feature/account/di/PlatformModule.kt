package com.jarroyo.feature.account.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.jarroyo.feature.account.sqldelight.AccountDatabase
import com.jarroyo.feature.account.sqldelight.DatabaseWrapper
import org.koin.dsl.module
import java.io.File

actual fun accountModule() = module {
    single {
        val dbDir = File(System.getProperty("user.home"), ".myAppData")
        dbDir.mkdirs()
        val dbFile = File(dbDir, "Accounts.db")
        val url = "jdbc:sqlite:${dbFile.absolutePath}"
        val driver = JdbcSqliteDriver(url).also { AccountDatabase.Schema.create(it) }
        DatabaseWrapper(AccountDatabase(driver))
    }
}
