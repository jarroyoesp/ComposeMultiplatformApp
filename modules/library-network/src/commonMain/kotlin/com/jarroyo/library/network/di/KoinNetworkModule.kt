package com.jarroyo.library.network.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient
import org.koin.core.component.get
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory<OkHttpClient> {
        OkHttpClient.Builder().apply {
            retryOnConnectionFailure(false)
            callTimeout(NETWORK_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        }.build()
    }

    single<NormalizedCacheFactory> {
        MemoryCacheFactory(
            maxSizeBytes = MEMORY_CACHE_SIZE_IN_BYTES,
            expireAfterMillis = CACHE_EXPIRES_IN_MILLIS,
        )
    }
    single<ApolloClient> {
        val chainedCacheFactory: NormalizedCacheFactory = get()
        ApolloClient.Builder()
            .serverUrl("https://spacex-production.up.railway.app/")
            .okHttpClient(get())
            // .logCacheMisses(log = { Timber.d("Apollo cache miss: $it") })
            .apply { normalizedCache(chainedCacheFactory) }
            .build()
    }
}

private const val MEMORY_CACHE_SIZE_IN_BYTES = 10 * 1024 * 1024
private const val NETWORK_TIMEOUT_IN_SECONDS = 30L
private val CACHE_EXPIRES_IN_MILLIS = TimeUnit.HOURS.toMillis(1)
