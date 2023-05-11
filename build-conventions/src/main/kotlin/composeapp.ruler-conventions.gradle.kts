plugins {
    id("com.spotify.ruler")
}

ruler {
    abi.set("arm64-v8a")
    locale.set("en")
    screenDensity.set(480)
    sdkVersion.set(30)
    ownershipFile.set(rootProject.file("config/ruler/ownership.yaml"))
    defaultOwner.set("others")
}
