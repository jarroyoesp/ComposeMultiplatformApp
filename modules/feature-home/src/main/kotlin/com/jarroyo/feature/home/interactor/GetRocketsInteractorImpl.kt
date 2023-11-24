package com.jarroyo.feature.home.interactor

import android.accounts.NetworkErrorException
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.exception.ApolloException
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery
import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import javax.inject.Inject

internal class GetRocketsInteractorImpl @Inject constructor(
   /* private val apolloClient: ApolloClient,*/
) : GetRocketsInteractor {
    override suspend operator fun invoke(
        page: Int,
        pageSize: Int,
        fetchPolicy: FetchPolicy,
    ): Result<List<RocketsQuery.Rocket>?, Exception> = try {
            Err(NetworkErrorException("response.errors?.run { first().message }"))
    } catch (e: ApolloException) {
        Err(e)
    }
}
