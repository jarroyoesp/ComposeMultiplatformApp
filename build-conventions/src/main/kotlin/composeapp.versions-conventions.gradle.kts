import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.util.Locale

plugins {
    id("com.github.ben-manes.versions")
    id("nl.littlerobots.version-catalog-update")
}

versionCatalogUpdate {
    keep {
        keepUnusedVersions.set(true)
        keepUnusedLibraries.set(true)
        keepUnusedPlugins.set(true)
    }
}

val isNonStable: (String) -> Boolean = { version ->
    val stableKeyword = setOf("RELEASE", "FINAL", "GA").any { version.uppercase(Locale.ROOT).contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    !stableKeyword && !(version matches regex)
}

tasks.withType<DependencyUpdatesTask>() {
    gradleReleaseChannel = "current"
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}
