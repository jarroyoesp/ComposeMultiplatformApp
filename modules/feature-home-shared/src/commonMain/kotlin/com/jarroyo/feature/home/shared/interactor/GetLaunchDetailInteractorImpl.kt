package com.jarroyo.feature.home.shared.interactor

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.LaunchDetailQuery
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.feature.home.api.interactor.GetLaunchDetailInteractor
import com.jarroyo.library.network.api.ext.executeAndHandleErrors
import org.koin.core.component.KoinComponent

internal class GetLaunchDetailInteractorImpl(
    private val apolloClient: ApolloClient,
) : GetLaunchDetailInteractor, KoinComponent {
    override suspend operator fun invoke(
        id: String,
        fetchPolicy: FetchPolicy,
    ): Result<LaunchFragment?, Exception> = apolloClient
        .query(LaunchDetailQuery(id))
        .fetchPolicy(fetchPolicy)
        .executeAndHandleErrors { response ->
            Ok(response.data?.launch?.launchFragment)
        }
}
