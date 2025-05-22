package com.jarroyo.feature.schedules.ui.userselector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.jarroyo.feature.schedules.api.ext.getFullName
import com.jarroyo.feature.schedules.api.model.User
import com.jarroyo.library.ui.shared.component.SettingsMenuRow
import com.jarroyo.library.ui.shared.theme.Spacing

@Composable
fun UserSelectorItem(
    user: User,
    state: UserSelectorContract.State,
    modifier: Modifier = Modifier,
    onClick: (selected: Boolean) -> Unit = {},
    placeholder: Boolean = false,
) {
    val isCurrentlySelected = state.usersSelectedMap[user.id] != null
    SettingsMenuRow(
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.x02),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = user.getFullName(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    // placeholder = placeholder,
                )
            }
        },
        modifier = modifier,
        icon = {
            if (state.multipleSelection) {
                Checkbox(
                    checked = isCurrentlySelected,
                    onCheckedChange = { onClick(it) },
                )
            } else {
                RadioButton(
                    selected = isCurrentlySelected,
                    onClick = { onClick(!isCurrentlySelected) },
                )
            }
        },
        enabled = !placeholder,
        onClick = { onClick(!isCurrentlySelected) },
    )
}
