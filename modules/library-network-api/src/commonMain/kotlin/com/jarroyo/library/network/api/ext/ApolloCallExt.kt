package com.jarroyo.library.network.api.ext

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.exception.CacheMissException
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.toList

suspend fun <D : Operation.Data, V> ApolloCall<D>.executeAndHandleErrors(
    successBlock: suspend (ApolloResponse<D>) -> Result<V, Exception>,
): Result<V, Exception> {
    val responses = toFlow()
        .filterNot { it.exception is CacheMissException }
        .toList()

    val lastResponse = responses.last()

    if (isSuccessResponse(lastResponse)) {
        return successBlock(lastResponse)
    }

    return Err(Exception(lastResponse.exception))
}

private fun <D : Operation.Data> isSuccessResponse(response: ApolloResponse<D>): Boolean =
    !response.hasErrors() && response.exception == null ||
        /* response.executionContext[HttpInfo]?.statusCode == HttpURLConnection.HTTP_PARTIAL &&*/ response.data != null
