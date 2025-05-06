package com.jarroyo.library.ui.shared.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalMainScaffoldPadding: ProvidableCompositionLocal<MutableState<PaddingValues>> = compositionLocalOf { error("No PaddingValues provided") }
val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> = compositionLocalOf { error("No SnackbarHostState provided") }
