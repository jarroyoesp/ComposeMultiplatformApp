package com.jarroyo.feature.launches.api.interactor

import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment

interface GetLaunchesInteractor {
    suspend operator fun invoke(
        page: Int = 1,
        pageSize: Int = 20,
        fetchPolicy: FetchPolicy = FetchPolicy.NetworkFirst,
    ): Result<List<LaunchFragment>?, Exception>
}
