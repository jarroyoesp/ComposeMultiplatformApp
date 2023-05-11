plugins {
    id ("composeapp.android-feature-conventions")
}

android {
    namespace = "com.jarroyo.feature.home"
    resourcePrefix = "home_"
    defaultConfig {
        consumerProguardFiles ("$projectDir/proguard-rules.pro")
    }
}

dependencies {
    api (projects.modules.featureHomeApi)
    implementation (projects.modules.libraryNavigationApi)
    implementation (projects.modules.libraryNetworkApi)
}