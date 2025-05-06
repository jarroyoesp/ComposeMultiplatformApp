package com.jarroyo.feature.schedules.navigationsuite

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.jarroyo.library.ui.shared.component.MainNavigationBarItem

@Composable
fun RowScope.ScheduleListNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    val painter = BitmapPainter(ImageBitmap(20, 20))
    MainNavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = painter,  // painterResource(resource = painter),
        modifier = modifier,
        label = label,
    )
}
