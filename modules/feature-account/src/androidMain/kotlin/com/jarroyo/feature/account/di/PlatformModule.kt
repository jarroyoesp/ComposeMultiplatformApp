package com.jarroyo.feature.account.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jarroyo.feature.account.sqldelight.AccountDatabase
import com.jarroyo.feature.account.sqldelight.DatabaseWrapper
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

actual fun accountModule() = module {
    single {
        val driver = AndroidSqliteDriver(AccountDatabase.Schema, get(), "Accounts.db")
        DatabaseWrapper(AccountDatabase(driver))
    }
    single { Android.create() }
}
