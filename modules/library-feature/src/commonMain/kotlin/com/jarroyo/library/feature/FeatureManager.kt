package com.jarroyo.library.feature

class FeatureManager {
    private val _featureMap = mutableMapOf<String, Feature>()
    private val features: List<Feature> get() = _featureMap.values.toList()
    val featureMap : Map<String, Feature> = _featureMap

    inline fun <reified T : Feature> getFeature(): T {
        val key = T::class.simpleName ?: error("Feature class must have a simple name")
        return featureMap[key] as? T ?: error("No feature registered with key $key")
    }

    fun register(features: List<Feature>) {
        features.forEach { feature ->
            val key = feature::class.simpleName ?: error("Feature class must have a simple name")
            _featureMap.put(key, feature)?.let { error("Duplicate features ${feature.id} - ${it.id}") }
        }
    }

    suspend fun onUserLogin() {
        features.forEach { it.featureLifecycle.onLogin() }
    }

    suspend fun onUserLogout() {
        features.reversed().forEach { it.featureLifecycle.onLogout() }
    }
}
