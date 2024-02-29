package com.jarroyo.library.ui.shared.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eygraber.compose.placeholder.placeholder

fun Modifier.placeholder(visible: Boolean) = Modifier.placeholder(
    visible = visible,
    color = Color.Gray,
)
