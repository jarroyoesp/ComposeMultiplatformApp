import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("composeapp.multiplatform-library-conventions")
    alias(libs.plugins.apollo)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.jarroyo.composeapp.library.network.api"
    resourcePrefix = "network_api_"
    defaultConfig {
        consumerProguardFiles("$projectDir/proguard-network-api-consumer-rules.pro")
    }
}

apollo {
    service("spacex") {
        packageName.set("com.jarroyo.composeapp.library.network.api.graphql")
        generateApolloMetadata.set(true)
        decapitalizeFields.set(true)
        generateDataBuilders.set(true)
       mapScalar(
           "Date",
           "kotlin.time.Instant",
           "com.jarroyo.library.network.api.adapter.KotlinTimeInstantAdapter"
       )
        introspection {
            endpointUrl.set("https://spacex-production.up.railway.app/")
            schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
        }
    }
}

kotlin {
    sourceSets.all {
        languageSettings.optIn("kotlin.time.ExperimentalTime")
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.apollo)
            api(libs.apollo.adapters)
            api(libs.apollo.cache)
            implementation(libs.apollo.cache.sqlite)
            implementation(libs.jetbrains.kotlinx.serialization)
        }
    }
}
