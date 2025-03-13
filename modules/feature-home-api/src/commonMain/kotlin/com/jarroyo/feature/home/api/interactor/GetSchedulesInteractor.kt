package com.jarroyo.feature.home.api.interactor

import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import kotlinx.serialization.Serializable

interface GetSchedulesInteractor {
    suspend operator fun invoke(
    ): Result<List<String>?, Exception>
}

@Serializable
class Schedule(
    id: String,
    name: String,
)