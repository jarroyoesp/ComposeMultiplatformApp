package com.jarroyo.feature.schedules.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jarroyo.feature.schedules.api.model.Schedule
import com.jarroyo.feature.schedules.ui.list.ScheduleListContract.Event
import com.jarroyo.library.ui.shared.component.placeholder
import com.jarroyo.library.ui.shared.theme.Spacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleListItem(
    item: Schedule,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false,
) {
    Card(
        onClick = { sendEvent(Event.OnItemClicked(checkNotNull(item.id))) },
        modifier = Modifier.then(modifier),
        enabled = !placeholder,
        elevation = Spacing.quarter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.x02),
            verticalArrangement = Arrangement.spacedBy(Spacing.quarter),
        ) {
            Text(
                "- id: ${item.id}",
                modifier = Modifier.placeholder(placeholder),
            )
            Text(
                "- Time: ${item.time}",
                modifier = Modifier.placeholder(placeholder),
            )
            Text(
                "- Slots: ${item.slots}",
                modifier = Modifier.placeholder(placeholder),
            )
        }
    }
}
