package com.jarroyo.library.feature

data class FeatureLifecycle(
    val onLogin: suspend () -> Unit = {},
    val onLogout: suspend () -> Unit = {},
)
