package com.jarroyo.feature.home.shared.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jarroyo.composeapp.library.network.api.graphql.RocketsQuery
import com.jarroyo.feature.home.shared.home.HomeContract.Event

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeRocketItem(
    item: RocketsQuery.Rocket,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false,
) {
    Card(onClick = { sendEvent(Event.OnItemClicked("")) }) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = item.name.orEmpty(),
            )
        }
    }

}
