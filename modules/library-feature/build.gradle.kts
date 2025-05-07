plugins {
    id("composeapp.multiplatform-library-conventions")
}
android {
    namespace = "com.jarroyo.library.feature"
    resourcePrefix = "feature_"
    defaultConfig {
        consumerProguardFiles("$projectDir/proguard-feature-consumer-rules.pro")
    }
}

kotlin {
    sourceSets {
        androidMain.dependencies {
        }
        commonMain.dependencies {
            api(projects.modules.libraryNavigationApi)
        }
    }
}