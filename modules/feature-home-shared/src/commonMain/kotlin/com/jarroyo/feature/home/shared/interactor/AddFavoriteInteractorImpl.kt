package com.jarroyo.feature.home.shared.interactor

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.feature.home.api.interactor.AddFavoriteInteractor
import com.jarroyo.feature.home.shared.FavoriteRockets
import com.jarroyo.feature.home.shared.sqldelight.DatabaseWrapper
import com.jarroyo.feature.home.shared.sqldelight.dao.FavoriteRocketsDao
import org.koin.core.component.KoinComponent

internal class AddFavoriteInteractorImpl(
    private val databaseWrapper: DatabaseWrapper,
) : AddFavoriteInteractor, KoinComponent {
    override suspend operator fun invoke(
        launch: LaunchFragment,
    ): Result<Boolean, Exception> = try {
        FavoriteRocketsDao(checkNotNull(databaseWrapper.instance)).insert(
            FavoriteRockets(
                checkNotNull(launch.id), launch.mission_name,
            ),
        )
        Ok(true)
    } catch (e: Exception) {
        Err(e)
    }
}
