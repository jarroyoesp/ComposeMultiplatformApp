package com.jarroyo.feature.home.shared.interactor

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.exception.ApolloException
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.LaunchDetailQuery
import com.jarroyo.feature.home.api.interactor.GetLaunchDetailInteractor
import org.koin.core.component.KoinComponent

class GetLaunchDetailInteractorImpl(
    private val apolloClient: ApolloClient,
) : GetLaunchDetailInteractor, KoinComponent {
    override suspend operator fun invoke(
        id: String,
        fetchPolicy: FetchPolicy,
    ): Result<LaunchDetailQuery.Launch?, Exception> = try {
        val response: ApolloResponse<LaunchDetailQuery.Data> =
            apolloClient
                .query(LaunchDetailQuery(id))
                .fetchPolicy(fetchPolicy)
                .execute()
        if (!response.hasErrors()) {
            Ok(response.data?.launch)
        } else {
            Err(Exception(response.errors?.run { first().message }))
        }
    } catch (e: ApolloException) {
        Err(e)
    }
}
