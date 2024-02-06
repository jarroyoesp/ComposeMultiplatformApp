package com.jarroyo.feature.home.shared.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jarroyo.composeapp.library.network.api.graphql.LaunchesQuery
import com.jarroyo.feature.home.shared.ui.HomeContract.Event
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeItem(
    item: LaunchesQuery.Launch,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false,
) {
    Card(
        onClick = { sendEvent(Event.OnItemClicked(checkNotNull(item.id))) },
        modifier = Modifier.then(modifier),
        enabled = !placeholder,
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
            Text("- Date: ${LocalDateTime.ofInstant(item.launch_date_local, ZoneId.systemDefault())}")
            Text("- Link: ${item.links?.article_link}")
            KamelImage(
                resource = asyncPainterResource(item.links?.flickr_images?.firstOrNull().orEmpty()),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().heightIn(max = 150.dp),
                contentScale = ContentScale.Crop,
                onLoading = { CircularProgressIndicator() },
            )
        }
    }
}
