package com.jarroyo.feature.home.shared.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.logCacheMisses
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.okHttpClient
import com.jarroyo.feature.home.api.interactor.GetRocketsInteractor
import com.jarroyo.feature.home.shared.home.HomeViewModel
import com.jarroyo.feature.home.shared.interactor.GetRocketsInteractorImpl
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.net.http.HttpClient
import java.util.concurrent.TimeUnit

val featureHomeModule = module {

    factory<OkHttpClient> {
        OkHttpClient.Builder().apply {
            retryOnConnectionFailure(false)
            callTimeout(30, TimeUnit.SECONDS)
        }.build()
    }
    single<ApolloClient> {
        // var chainedCacheFactory = cacheFactorySet.firstOrNull()
        // if (cacheFactorySet.size > 1) {
        //     cacheFactorySet.drop(1).forEach { cacheFactory ->
        //         chainedCacheFactory = chainedCacheFactory?.chain(cacheFactory)
        //     }
        // }
        ApolloClient.Builder()
            .serverUrl("https://spacex-production.up.railway.app/")
            .okHttpClient(get())
            // .logCacheMisses(log = { Timber.d("Apollo cache miss: $it") })
            // .apply { chainedCacheFactory?.let { normalizedCache(it) } }
            .build()
    }
    factory<GetRocketsInteractor> { GetRocketsInteractorImpl(get()) }
    singleOf(::HomeViewModel)

}

class FeatureHomeKoinComponent : KoinComponent {
    val getRocketsInteractor: GetRocketsInteractor = get()
    val homeViewModel: HomeViewModel = get()
}