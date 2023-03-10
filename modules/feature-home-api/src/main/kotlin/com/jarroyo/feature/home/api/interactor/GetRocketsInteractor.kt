package com.jarroyo.feature.home.api.interactor

import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery

interface GetRocketsInteractor {
    suspend operator fun invoke(
        page: Int = 1,
        pageSize: Int = 20,
        fetchPolicy: FetchPolicy = FetchPolicy.NetworkFirst,
    ): Result<List<RocketsQuery.Rocket>?, Boolean>
}
