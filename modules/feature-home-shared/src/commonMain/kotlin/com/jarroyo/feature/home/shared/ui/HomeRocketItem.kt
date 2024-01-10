package com.jarroyo.feature.home.shared.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jarroyo.composeapp.library.network.api.graphql.LaunchesQuery
import com.jarroyo.feature.home.shared.ui.HomeContract.Event
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeRocketItem(
    item: LaunchesQuery.Launch,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { sendEvent(Event.OnItemClicked(checkNotNull(item.id))) },
        modifier = Modifier.then(modifier),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(item.mission_name.orEmpty(), fontWeight = FontWeight.Bold)
            Text("- Rocket: ${item.rocket?.rocket?.rocketFragment?.name.orEmpty()}")
            Text("- Company: ${item.rocket?.rocket?.rocketFragment?.name.orEmpty()}")
            KamelImage(
                resource = asyncPainterResource(item.links?.flickr_images?.firstOrNull().orEmpty()),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().heightIn(max = 100.dp),
                contentScale = ContentScale.Crop,
                onLoading = { CircularProgressIndicator() },
                onFailure = {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
