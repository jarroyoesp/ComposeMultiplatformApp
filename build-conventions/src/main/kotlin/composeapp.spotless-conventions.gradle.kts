import com.diffplug.gradle.spotless.SpotlessTask
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id ("com.diffplug.spotless")
}
val libs = the<LibrariesForLibs>()

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt", "**/nativeMain/**/*.kt", "**/desktopMain/**/*.kt")
        diktat(libs.versions.diktat.get()).configFile("$rootDir/config/diktat/diktat-analysis.yml")
        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
    }

    format ("graphql") {
        target("**/*.graphql")
        prettier(libs.versions.prettier.get()).configFile("$rootDir/config/prettier/prettierrc-graphql.yml")
    }

    format ("yml") {
        target ("**/*.yml", "**/*.yaml")
        prettier(libs.versions.prettier.get()).configFile("$rootDir/config/prettier/prettierrc-yml.yml")
    }

    format ("androidXml") {
        target ("**/AndroidManifest.xml", "src/**/*.xml")
        targetExclude ("**/mergedManifests/**/AndroidManifest.xml", "**/build/**/*.xml")
        leadingTabsToSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }

    format ("misc") {
        // define the files to apply `misc` to
        target ("**/*.md", "**/.gitignore")

        // define the steps to apply to those files
        leadingTabsToSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks {
    withType<SpotlessTask> {
        mustRunAfter(":app:copyMergedManifests")
    }
}

