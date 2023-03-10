package com.jarroyo.feature.home.interactor

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.exception.ApolloException
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery
import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import javax.inject.Inject

internal class GetRocketsInteractorImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : GetRocketsInteractor {
    override suspend operator fun invoke(
        page: Int,
        pageSize: Int,
        fetchPolicy: FetchPolicy,
    ): Result<List<RocketsQuery.Rocket>?, Boolean> = try {
        val response: ApolloResponse<RocketsQuery.Data> =
            apolloClient
                .query(RocketsQuery())
                .fetchPolicy(fetchPolicy)
                .execute()
        if (!response.hasErrors()) {
            Ok(response.data?.rockets?.filterNotNull())
        } else {
            Err(true)
        }
    } catch (e: ApolloException) {
        Err(true)
    }
}
