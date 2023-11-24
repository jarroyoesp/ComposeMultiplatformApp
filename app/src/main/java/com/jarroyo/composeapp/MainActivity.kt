package com.jarroyo.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.jarroyo.composeapp.di.initKoin
import com.jarroyo.composeapp.ui.navigation.NavGraph
import com.jarroyo.composeapp.ui.theme.ComposeAppTheme
import com.jarroyo.library.navigation.api.navigator.AppNavigator
import com.jarroyo.library.navigation.api.navigator.NavigatorEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.koin.androidContext
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin {
            androidContext(applicationContext)
        }
        setContent {
            ComposeAppTheme {
                MainScreen(appNavigator)
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun MainScreen(
    appNavigator: AppNavigator,
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navHostController = rememberNavController(bottomSheetNavigator)
    LaunchedEffect(navHostController) {
        appNavigator.destinations.onEach { event ->
            when (event) {
                is NavigatorEvent.Directions -> navHostController.navigate(
                    event.destination,
                    event.builder,
                ).also { Timber.d("Navigate to ${event.destination}") }
                is NavigatorEvent.HandleDeepLink -> TODO()
                is NavigatorEvent.NavigateBack -> TODO()
                is NavigatorEvent.NavigateUp -> TODO()
            }
        }.launchIn(this)
    }
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        ModalBottomSheetLayout(
            bottomSheetNavigator = bottomSheetNavigator,
            sheetBackgroundColor = MaterialTheme.colorScheme.background,
        ) {
            NavGraph(navController = navHostController)
        }
    }
}
