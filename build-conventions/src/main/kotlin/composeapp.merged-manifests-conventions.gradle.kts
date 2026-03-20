tasks {
    register<Copy>("copyMergedManifests") {
        dependsOn(tasks.matching { it.name matches "^process.*Manifest$".toRegex() })
        mustRunAfter(tasks.matching { it.name matches "^process.*Manifest$".toRegex() })
        from(layout.buildDirectory.dir("intermediates/merged_manifests")) {
            include("**/*.xml")
        }
        into("versions/mergedManifests")
        doFirst {
            mkdir("versions/mergedManifests")
        }
        filter { line -> line.replace("(android:version.*=\".*\")|(android:testOnly=\".*\")".toRegex(), "") }
    }

    named("check") {
        dependsOn(tasks.named("copyMergedManifests"))
    }
}
