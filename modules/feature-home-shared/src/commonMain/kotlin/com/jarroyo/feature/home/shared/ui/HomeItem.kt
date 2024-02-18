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
import com.jarroyo.library.ui.shared.component.placeholder
import com.jarroyo.library.ui.shared.theme.Spacing
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
                .padding(Spacing.x02)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = item.mission_name.orEmpty(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.placeholder(placeholder),
            )
            Text(
                "- Rocket: ${item.rocket?.rocket?.rocketFragment?.name.orEmpty()}",
                modifier = Modifier.placeholder(placeholder),
            )
            Text(
                "- Company: ${item.rocket?.rocket?.rocketFragment?.name.orEmpty()}",
                modifier = Modifier.placeholder(placeholder),
            )
            Text(
                "- Date: ${
                    LocalDateTime.ofInstant(
                        item.launch_date_local,
                        ZoneId.systemDefault(),
                    )
                }",
                modifier = Modifier.placeholder(placeholder),
            )
            Text(
                "- Link: ${item.links?.article_link}",
                modifier = Modifier.placeholder(placeholder),
            )
            KamelImage(
                resource = asyncPainterResource(item.links?.flickr_images?.firstOrNull().orEmpty()),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 150.dp)
                    .placeholder(
                        visible = placeholder,
                    ),
                contentScale = ContentScale.Crop,
                onLoading = { CircularProgressIndicator() },
            )
        }
    }
}
