package com.jarroyo.feature.launches.interactor

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.LaunchesQuery
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.feature.launches.api.interactor.GetLaunchesInteractor
import com.jarroyo.library.network.api.ext.executeAndHandleErrors

internal class GetLaunchesInteractorImpl(
    private val apolloClient: ApolloClient,
) : GetLaunchesInteractor {
    override suspend operator fun invoke(
        page: Int,
        pageSize: Int,
        fetchPolicy: FetchPolicy,
    ): Result<List<LaunchFragment>?, Exception> = apolloClient
        .query(LaunchesQuery())
        .fetchPolicy(fetchPolicy)
        .executeAndHandleErrors { response ->
            Ok(response.data?.launches?.filterNotNull()?.map { it.launchFragment })
        }
}
