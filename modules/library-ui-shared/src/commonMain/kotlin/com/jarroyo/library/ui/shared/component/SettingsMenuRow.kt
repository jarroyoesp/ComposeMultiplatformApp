package com.jarroyo.library.ui.shared.component

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jarroyo.library.ui.shared.theme.Spacing

@Composable
fun SettingsMenuRow(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    icon: (@Composable () -> Unit)? = null,
    subtitle: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
    ) {
        val alpha = if (enabled) 1f else ContentAlpha.disabled
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .height(IntrinsicSize.Min)
                .combinedClickable(
                    enabled = enabled,
                    onClick = onClick,
                    onLongClick = onLongClick,
                )
                .padding(vertical = Spacing.x01),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                SettingsTileIcon(
                    icon = icon,
                    modifier = Modifier.alpha(alpha),
                )
            } else {
                Spacer(modifier = Modifier.size(Spacing.x02))
            }
            SettingsTileTexts(
                title = title,
                subtitle = subtitle,
                modifier = Modifier.alpha(alpha),
            )
            if (action != null) {
                VerticalDivider(
                    modifier = Modifier
                        .padding(vertical = Spacing.half)
                        .height(56.dp),
                )
                SettingsTileAction {
                    action.invoke()
                }
            }
        }
    }
}
