package com.jarroyo.feature.home.shared.interactor

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.exception.ApolloException
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.LaunchesQuery
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.feature.home.api.interactor.GetLaunchesInteractor

internal class GetLaunchesInteractorImpl(
    private val apolloClient: ApolloClient,
) : GetLaunchesInteractor {
    override suspend operator fun invoke(
        page: Int,
        pageSize: Int,
        fetchPolicy: FetchPolicy,
    ): Result<List<LaunchFragment>?, Exception> = try {
        val response: ApolloResponse<LaunchesQuery.Data> =
            apolloClient
                .query(LaunchesQuery())
                .fetchPolicy(fetchPolicy)
                .execute()
        if (!response.hasErrors()) {
            Ok(response.data?.launches?.filterNotNull()?.map { it.launchFragment })
        } else {
            Err(Exception(response.errors?.run { first().message }))
        }
    } catch (e: ApolloException) {
        Err(e)
    }
}
