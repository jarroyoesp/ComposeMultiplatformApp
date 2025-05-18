package com.jarroyo.library.ui.shared.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.jarroyo.library.navigation.api.destination.NavigationDestination
import kotlinx.serialization.json.Json

val LocalNavHostController: ProvidableCompositionLocal<NavHostController> =
    compositionLocalOf { error("No NavHostController provided") }

inline fun <reified T> NavHostController.setResult(key: String, value: T): Boolean {
    val json = Json.encodeToString(value)
    return previousBackStackEntry?.run { savedStateHandle[key] = json } != null
}

@Composable
inline fun <reified T : NavigationDestination.Result> NavHostController.observeResult(
    key: String,
    crossinline onResult: (T) -> Unit
): Boolean =
    currentBackStackEntry?.run {
        LaunchedEffect(Unit) {
            savedStateHandle.getStateFlow<String?>(key, null).collect { result ->
                result?.let {
                    val response = Json.decodeFromString<T>(result)
                    if (!response.consumed) {
                        response.consumed = true
                        onResult(response)
                    }
                }
            }
        }
    } != null
