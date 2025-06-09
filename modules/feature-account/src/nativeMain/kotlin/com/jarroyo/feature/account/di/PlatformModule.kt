package com.jarroyo.feature.account.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.jarroyo.feature.account.sqldelight.AccountDatabase
import com.jarroyo.feature.account.sqldelight.DatabaseWrapper
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun accountModule() = module {
    single {
        val driver = NativeSqliteDriver(AccountDatabase.Schema, "Accounts.db")
        DatabaseWrapper(AccountDatabase(driver))
    }
    single { Darwin.create() }
}
