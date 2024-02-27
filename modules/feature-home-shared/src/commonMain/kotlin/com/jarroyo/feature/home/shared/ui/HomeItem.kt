package com.jarroyo.feature.home.shared.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeItem(
    item: LaunchesQuery.Launch,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
    favoritesList: List<String>? = null,
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.x01),
            ) {
                Text(
                    text = item.mission_name.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.placeholder(placeholder),
                )
                if (favoritesList?.contains(item.id) == true) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                    )
                }
            }

            Text(
                "- Rocket: ${item.rocket?.rocket?.rocketFragment?.name.orEmpty()}",
                modifier = Modifier.placeholder(placeholder),
            )
            Text(
                "- Company: ${item.rocket?.rocket?.rocketFragment?.name.orEmpty()}",
                modifier = Modifier.placeholder(placeholder),
            )
            Text(
                "- Date: ${item.launch_date_local?.toLocalDateTime(TimeZone.UTC)}",
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
                    .placeholder(
                        visible = placeholder,
                    ),
                contentScale = ContentScale.FillWidth,
                onLoading = { CircularProgressIndicator() },
            )
        }
    }
}
