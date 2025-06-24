package com.jarroyo.library.ui.shared.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import composeapp.modules.library_ui_shared.generated.resources.Res
import androidx.compose.ui.text.style.TextAlign
import com.jarroyo.library.ui.shared.theme.Spacing
import com.jarroyo.library.ui.shared.theme.textSecondary
import composeapp.modules.library_ui_shared.generated.resources.ui_ic_empty
import org.jetbrains.compose.resources.painterResource

@Composable
fun EmptyState(
    text: String,
    modifier: Modifier = Modifier,
    image: Painter? = null,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = Spacing.x02, vertical = if (image == null) Spacing.x02 else Spacing.x04),
        verticalArrangement = Arrangement.spacedBy(Spacing.x02),
    ) {
        image?.let { painter ->
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.textSecondary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun EmptyStateWithImage(
    text: String,
    modifier: Modifier = Modifier,
    image: Painter = painterResource(Res.drawable.ui_ic_empty),
    onClick: (() -> Unit)? = null,
) {
    EmptyState(
        text = text,
        image = image,
        modifier = modifier,
        onClick = onClick,
    )
}
