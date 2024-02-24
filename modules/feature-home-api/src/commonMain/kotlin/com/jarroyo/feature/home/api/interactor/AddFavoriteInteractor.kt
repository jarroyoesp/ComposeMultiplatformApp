package com.jarroyo.feature.home.api.interactor

import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.LaunchDetailQuery

interface AddFavoriteInteractor {
    suspend operator fun invoke(
        launch: LaunchDetailQuery.Launch,
    ): Result<Boolean, Exception>
}
