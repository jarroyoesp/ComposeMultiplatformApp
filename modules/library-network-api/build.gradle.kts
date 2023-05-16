import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id ("composeapp.android-library-conventions")
    alias(libs.plugins.apollo)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.jarroyo.composeapp.library.network.api"
    resourcePrefix = "network_api_"
    defaultConfig {
        consumerProguardFiles ("$projectDir/proguard-network-api-consumer-rules.pro")
    }
}

apollo {
    service("spacex") {
        packageName.set("com.jarroyo.composeapp.library.network.api.graphql")
        generateApolloMetadata.set(true)
        // JAE  codegenModels = "experimental_operationBasedWithInterfaces"
        // JAE decapitalizeFields = true
        generateDataBuilders.set(true)
        // JAE mapScalar("ISODate", "java.time.LocalDate", "com.apollographql.apollo3.adapter.JavaLocalDateAdapter")
        // JAE mapScalar("ISODateTime", "java.time.Instant", "com.apollographql.apollo3.adapter.JavaInstantAdapter")
    }
}

dependencies {
    // JAE implementation projects.modules.libraryAndroidApi

    api (libs.apollo)
    api (libs.apollo.adapters)
    api (libs.apollo.cache)
    api (libs.retrofit)
    implementation (libs.apollo.cache.sqlite)
    implementation (libs.kotlinx.serialization)
}

// Workaround for https://github.com/detekt/detekt/issues/4743
tasks {
    withType<Detekt>().configureEach {
        exclude("com/jarroyo/composeapp/library/network/api/graphql/**/*.kt")
    }
    register<Exec>("refreshGraphQlSchema") {
        val endpoint = "https://spacex-production.up.railway.app/"
        val schemaPath = "modules/library-network-api/src/main/graphql/schema.graphqls"
        workingDir(rootDir)
        commandLine(
            "./gradlew",
            ":module:library-network-api:downloadApolloSchema",
            "--endpoint=$endpoint",
            "--schema=$schemaPath",
        )
    }
}