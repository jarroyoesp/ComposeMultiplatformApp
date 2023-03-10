package com.jarroyo.library.network.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo3.cache.normalized.logCacheMisses
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.okHttpClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [NetworkModule.BindModule::class])
@InstallIn(SingletonComponent::class)
open class NetworkModule {
    @Provides
    @Singleton
    fun provideJson(serializerModule: SerializersModule) = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        serializersModule = serializerModule
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideScalarsConverterFactory(): Converter.Factory = ScalarsConverterFactory.create()

    @Provides
    @Singleton
    @IntoSet
    fun provideKotlinxSerializationConverterFactory(json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        retryOnConnectionFailure(false)
        callTimeout(NETWORK_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
    }.build()

    @Provides
    @Singleton
    @IntoSet
    fun provideApolloMemoryCache(): NormalizedCacheFactory = MemoryCacheFactory(
        maxSizeBytes = MEMORY_CACHE_SIZE_IN_BYTES,
        expireAfterMillis = CACHE_EXPIRES_IN_MILLIS,
    )

    @Provides
    @Singleton
    fun provideApolloClient(
        httpClient: OkHttpClient,
        cacheFactorySet: Set<@JvmSuppressWildcards NormalizedCacheFactory>,
    ): ApolloClient {
        var chainedCacheFactory = cacheFactorySet.firstOrNull()
        if (cacheFactorySet.size > 1) {
            cacheFactorySet.drop(1).forEach { cacheFactory ->
                chainedCacheFactory = chainedCacheFactory?.chain(cacheFactory)
            }
        }
        return ApolloClient.Builder()
            .serverUrl("https://spacex-production.up.railway.app/")
            .okHttpClient(httpClient)
            .logCacheMisses(log = { Timber.d("Apollo cache miss: $it") })
            .apply { chainedCacheFactory?.let { normalizedCache(it) } }
            .build()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface BindModule

    companion object {
        private const val MEMORY_CACHE_SIZE_IN_BYTES = 10 * 1024 * 1024
        private const val NETWORK_TIMEOUT_IN_SECONDS = 30L
        private val CACHE_EXPIRES_IN_MILLIS = TimeUnit.HOURS.toMillis(1)
    }
}
