package com.jarroyo.feature.home.shared.interactor

import co.touchlab.kermit.Logger
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.jarroyo.composeapp.library.network.api.graphql.LaunchesQuery
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
import com.jarroyo.feature.home.api.interactor.GetLaunchesInteractor
import com.jarroyo.feature.home.api.interactor.GetSchedulesInteractor
import com.jarroyo.feature.home.api.interactor.Schedule
import com.jarroyo.library.network.api.ext.executeAndHandleErrors
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

internal class GetSchedulesInteractorImpl: GetSchedulesInteractor {

    private  val firestore = Firebase.firestore
    override suspend operator fun invoke(): Result<List<String>?, Exception> {
        val schedule = firestore.collection("Schedules").get().documents.get(0).data<String>()
            return Ok(listOf(schedule))
        }
}
