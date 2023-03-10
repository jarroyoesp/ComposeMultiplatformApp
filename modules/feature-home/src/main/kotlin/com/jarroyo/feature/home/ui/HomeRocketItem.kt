package com.jarroyo.feature.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.placeholder
import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery
import com.jarroyo.feature.home.ui.HomeContract.Event
import com.jarroyo.library.ui.theme.Spacing

@Composable
fun HomeRocketItem(
    item: RocketsQuery.Rocket,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false,
) {
    Surface(
        modifier = Modifier
            .clickable { sendEvent(Event.OnAcceptButtonClicked) }
            .then(modifier),
    ) {
        Row(
            modifier = Modifier
                .padding(Spacing.x02)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 400.dp),
            horizontalArrangement = Arrangement.spacedBy(Spacing.x01),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item.company.orEmpty(),
                modifier = Modifier.placeholder(placeholder, Color.LightGray),
            )
            Text(
                text = item.name.orEmpty(),
                modifier = Modifier.weight(1f).placeholder(placeholder, Color.LightGray),
            )
        }
    }
}
