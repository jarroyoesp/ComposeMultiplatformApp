// Sharing build logic between subprojects
// https://docs.gradle.org/current/samples/sample_convention_plugins.html

plugins {
    `kotlin-dsl`
}


dependencies {
    implementation (libs.plugin.aboutlibraries)
    implementation (libs.plugin.android.gradle)
    implementation (libs.plugin.androidcachefix)
    implementation (libs.plugin.appversioning)
    implementation (libs.plugin.detekt)
    implementation (libs.plugin.easylauncher)
    implementation (libs.plugin.firebase.crashlytics)
    implementation (libs.plugin.firebase.perf)
    implementation (libs.plugin.google.services)
    implementation (libs.plugin.hilt)
    implementation (libs.plugin.kotlin)
    implementation (libs.plugin.ksp)
    implementation (libs.plugin.ruler)
    implementation (libs.plugin.spotless)
    implementation(libs.plugin.versions)
    implementation(libs.plugin.versions.update)
    implementation (libs.plugin.violation)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
