import se.bjurr.violations.comments.github.plugin.gradle.ViolationCommentsToGitHubTask

plugins {
    id("se.bjurr.violations.violation-comments-to-github-gradle-plugin")
}

tasks.register<ViolationCommentsToGitHubTask>("violationCommentsToGitHub") {
    setPullRequestId(System.getProperties()["GITHUB_PULLREQUESTID"] as String?)
    setoAuth2Token(System.getProperties()["GITHUB_OAUTH2TOKEN"] as String?)
    setGitHubUrl("https://api.github.com/")
    setCreateCommentWithAllSingleFileComments(false)
    setCreateSingleFileComments(true)
    setCommentOnlyChangedContent(true)
    setKeepOldComments(false)
    setViolations(
            listOf(
                    listOf(
                            "KOTLINGRADLE",
                            ".",
                            ".*/build/logs/buildlog.*\\.txt\$",
                            "Gradle",
                    ),
                    listOf(
                            "CHECKSTYLE",
                            ".",
                            ".*/reports/detekt/.*\\.xml\$",
                            "Detekt",
                    ),
                    listOf(
                            "ANDROIDLINT",
                            ".",
                            ".*/reports/lint-results.*\\.xml\$",
                            "Android Lint",
                    ),
                    listOf(
                            "JUNIT",
                            ".",
                            ".*/build/test-results/test.*/.*\\.xml\$",
                            "JUnit",
                    ),
                    listOf(
                            "JUNIT",
                            ".",
                            ".*/build/sauce/saucectl-report\\.xml\$",
                            "Espresso",
                    ),
            ),
    )
}
