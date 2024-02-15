@file:OptIn(ApolloExperimental::class)

package com.jarroyo.feature.home.shared.interactor

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestNetworkError
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.github.michaelbull.result.unwrap
import com.github.michaelbull.result.unwrapError
import com.jarroyo.composeapp.library.network.api.graphql.LaunchDetailQuery
import com.jarroyo.feature.home.api.interactor.GetLaunchDetailInteractor
import io.mockk.clearAllMocks
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class GetLaunchDetailInteractorImplTest {
    private val data: LaunchDetailQuery.Data = LaunchDetailQuery.Data { }
    private lateinit var apolloClient: ApolloClient
    private lateinit var getLaunchDetailInteractor: GetLaunchDetailInteractor

    @BeforeTest
    fun setUp() {
        apolloClient = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()

        getLaunchDetailInteractor = GetLaunchDetailInteractorImpl(apolloClient)
    }

    @AfterTest
    fun tearDown() {
        clearAllMocks()
        apolloClient.close()
    }

    @Test
    fun GIVEN_Success_WHEN_call_interactor_THEN_return_success() = runTest {
        // Given
        enqueueApolloSuccessResponse()

        // When
        val response = getLaunchDetailInteractor("id")

        // Then
        assertEquals(data.launch, response.unwrap())
    }

    @Test
    fun GIVEN_Error_WHEN_call_interactor_THEN_return_error() = runTest {
        // Given
        apolloClient.enqueueTestNetworkError()

        // When
        val response = getLaunchDetailInteractor("id")

        // Then
        assertIs<Exception>(response.unwrapError())
    }

    private fun enqueueApolloSuccessResponse() {
        val query = LaunchDetailQuery("id")
        apolloClient.enqueueTestResponse(query, data)
    }
}
