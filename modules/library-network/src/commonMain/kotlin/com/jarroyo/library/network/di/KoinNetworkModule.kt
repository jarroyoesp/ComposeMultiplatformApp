package com.jarroyo.library.network.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val networkModule = module {
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
            .apply { normalizedCache(chainedCacheFactory) }
            .build()
    }

    single<FirebaseFirestore> {
        Firebase.firestore
    }
}

private const val MEMORY_CACHE_SIZE_IN_BYTES = 10 * 1024 * 1024
private const val CACHE_EXPIRES_IN_MILLIS = 60*60*1000L
