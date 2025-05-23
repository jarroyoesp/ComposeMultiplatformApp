package com.jarroyo.library.ui.shared.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jarroyo.library.ui.shared.theme.Spacing

@Composable
fun BottomButtonBar(
    modifier: Modifier = Modifier,
    tonalElevation: Dp = 8.dp,
    shadowElevation: Dp = 0.dp,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.x02, vertical = Spacing.x01),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}
