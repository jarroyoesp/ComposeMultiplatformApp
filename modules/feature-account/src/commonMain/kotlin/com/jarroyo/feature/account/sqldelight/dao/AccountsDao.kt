package com.jarroyo.feature.account.sqldelight.dao

import co.touchlab.kermit.Logger
import com.jarroyo.feature.account.sqldelight.AccountDatabase
import com.jarroyo.feature.account.Account

class AccountsDao(database: AccountDatabase) {
    private val db = database.accountQueries

    internal fun insert(item: Account) {
        Logger.d("Insert: $item")
        db.insertAccount(item.id, item.username)
    }

    internal fun removeItem(id: String) {
        Logger.d("RemoveItem: $id")
        db.removeAccount(id)
    }

    internal fun deleteAll() {
        db.removeAllAccount()
    }

    internal fun select(): List<Account> = db.selectAccount().executeAsList()
}
