package com.jarroyo.feature.home.api.interactor

import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment

interface AddFavoriteInteractor {
    suspend operator fun invoke(
        launch: LaunchFragment,
    ): Result<Boolean, Exception>
}
