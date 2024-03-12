package com.jarroyo.feature.home.shared.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.home.api.interactor.GetFavoritesInteractor
import com.jarroyo.feature.home.shared.sqldelight.DatabaseWrapper
import com.jarroyo.feature.home.shared.sqldelight.dao.FavoriteRocketsDao

internal class GetFavoritesInteractorImpl(
    private val databaseWrapper: DatabaseWrapper,
) : GetFavoritesInteractor {
    override suspend operator fun invoke(): Result<List<String>, Exception> = try {
        Ok(FavoriteRocketsDao(checkNotNull(databaseWrapper.instance)).select().map { it.id })
    } catch (e: Exception) {
        Err(e)
    }
}
