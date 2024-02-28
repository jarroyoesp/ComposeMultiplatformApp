package com.jarroyo.feature.home.api.interactor

import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment

interface GetLaunchDetailInteractor {
    suspend operator fun invoke(
        id: String,
        fetchPolicy: FetchPolicy = FetchPolicy.CacheFirst,
    ): Result<LaunchFragment?, Exception>
}
