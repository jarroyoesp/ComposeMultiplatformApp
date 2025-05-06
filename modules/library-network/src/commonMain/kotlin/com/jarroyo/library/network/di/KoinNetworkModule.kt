package com.jarroyo.library.network.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf

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
    singleOf(::createJson)
    single<HttpClient> { createHttpClient(get(), get()) }

    factory { ElectricityApi(get()) }
}

fun createJson() = Json { isLenient = true
 ignoreUnknownKeys = true }

fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json) =
    HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }
    }

private const val MEMORY_CACHE_SIZE_IN_BYTES = 10 * 1024 * 1024
private const val CACHE_EXPIRES_IN_MILLIS = 60*60*1000L
