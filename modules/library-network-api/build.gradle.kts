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
            "kotlinx.datetime.Instant",
            "com.apollographql.apollo.adapter.KotlinxInstantAdapter"
        )
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.apollo)
            api(libs.apollo.adapters)
            api(libs.apollo.cache)
            api(libs.retrofit)
            implementation(libs.apollo.cache.sqlite)
            implementation(libs.kotlinx.serialization)
        }
    }
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