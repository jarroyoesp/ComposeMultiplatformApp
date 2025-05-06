package com.jarroyo.feature.launches.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.feature.launches.api.interactor.RemoveFavoriteInteractor
import com.jarroyo.feature.launches.sqldelight.DatabaseWrapper
import com.jarroyo.feature.launches.sqldelight.dao.FavoriteRocketsDao

internal class RemoveFavoriteInteractorImpl(
    private val databaseWrapper: DatabaseWrapper,
) : RemoveFavoriteInteractor {
    override suspend operator fun invoke(
        id: String,
    ): Result<Boolean, Exception> = try {
        FavoriteRocketsDao(checkNotNull(databaseWrapper.instance)).removeItem(id)
        Ok(true)
    } catch (e: Exception) {
        Err(e)
    }
}
