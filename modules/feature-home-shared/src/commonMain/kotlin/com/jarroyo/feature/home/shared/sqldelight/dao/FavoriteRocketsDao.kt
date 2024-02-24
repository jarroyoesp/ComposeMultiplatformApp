package com.jarroyo.feature.home.shared.sqldelight.dao

import co.touchlab.kermit.Logger
import com.jarroyo.feature.home.shared.FavoriteRockets
import com.jarroyo.feature.home.shared.sqldelight.Database

class FavoriteRocketsDao(database: Database) {
    private val db = database.favoriteRocketsQueries

    internal fun insert(item: FavoriteRockets) {
        Logger.d("Insert: $item")
        db.insertFavorite(item.id, item.title)
    }

    internal fun removeItem(id: String) {
        Logger.d("RemoveItem: $id")
        db.removeFavorite(id)
    }

    internal fun deleteAll() {
        db.removeAllFavoritesRockets()
    }

    internal fun select(): List<FavoriteRockets> = db.selectFavorites().executeAsList()
}
