tasks {
    register<Copy>("copyMergedManifests") {
        dependsOn(tasks.matching { it.name matches "^process.*Manifest$".toRegex() })
        mustRunAfter(tasks.matching { it.name matches "^process.*Manifest$".toRegex() })
        mkdir("versions/mergedManifests")
        from("$buildDir/intermediates/merged_manifests") {
            include("**/*.xml")
        }
        into("versions/mergedManifests")
        filter { line -> line.replace("(android:version.*=\".*\")|(android:testOnly=\".*\")".toRegex(), "") }
    }

    named("check") {
        dependsOn(tasks.named("copyMergedManifests"))
    }
}
