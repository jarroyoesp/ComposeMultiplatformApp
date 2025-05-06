package com.jarroyo.library.ui.shared.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun RowScope.NavigationBarItemWithBadge(
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier,
    label: String? = null,
    badgeCount: Int? = null,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
           // BadgedBox(
           // badge = {
           // badgeCount?.let { count ->
           // Badge(
           // modifier = Modifier.padding(top = Spacing.x01),
           // ) {
           // Text(count.toString())
           // }
           // }
           // },
           // ) {
           // Icon(
           // painter = icon,
           // contentDescription = label,
           // )
           // }
        },
        modifier = modifier,
        label = label?.let {
            {
                Text(
                    text = label,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                )
            }
        },
        colors = colors,
    )
}

@Composable
fun RowScope.MainNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier,
    label: String? = null,
    badgeCount: Int? = null,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
) {
    NavigationBarItemWithBadge(
        selected = selected,
        onClick = onClick,
        icon = icon,
        modifier = modifier,
        label = label,
        badgeCount = badgeCount,
        colors = colors,
    )
}
