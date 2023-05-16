import gradle.kotlin.dsl.accessors._c46661f21c97c0af88bb9497324000a7.testImplementation
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.the

plugins {
    id ("com.android.library")
    id ("composeapp.android-conventions")
}

val libs = the<LibrariesForLibs>()

dependencies {
    implementation (libs.kotlin.result)
    implementation (libs.kotlin.result.coroutines)
    implementation (libs.timber)

    // androidTestImplementation projects.modules.libraryTestAndroid
    androidTestUtil (libs.androidx.test.orchestrator)
    testImplementation(project(":modules:library-test"))
    androidTestImplementation(project(":modules:library-test"))
}
