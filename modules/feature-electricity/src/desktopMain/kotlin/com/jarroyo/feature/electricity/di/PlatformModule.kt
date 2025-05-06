package com.jarroyo.feature.electricity.di

import org.koin.dsl.module
import io.ktor.client.*
import io.ktor.client.engine.java.*


actual fun electricityModule() = module {
    single { Java.create() }
}
