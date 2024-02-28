package com.jarroyo.feature.home.shared.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jarroyo.composeapp.library.network.api.graphql.fragment.LaunchFragment
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
    item: LaunchFragment,
    sendEvent: (event: Event) -> Unit,
    modifier: Modifier = Modifier,
    favoritesList: List<String>? = null,
    placeholder: Boolean = false,
) {
    Card(
        onClick = { sendEvent(Event.OnItemClicked(checkNotNull(item.id))) },
        modifier = Modifier.then(modifier),
        enabled = !placeholder,
        elevation = Spacing.quarter,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            KamelImage(
                resource = asyncPainterResource(item.links?.flickr_images?.firstOrNull().orEmpty()),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, max = 50.dp)
                    .placeholder(
                        visible = placeholder,
                    ),
                contentScale = ContentScale.Crop,
                onLoading = { CircularProgressIndicator() },
            )
            Column(
                modifier = Modifier.padding(Spacing.x02),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.x01),
                ) {
                    Text(
                        text = item.mission_name.orEmpty(),
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.placeholder(placeholder).weight(5f),
                    )
                    if (favoritesList?.contains(item.id) == true) {
                        Box(
                            modifier = Modifier.placeholder(placeholder).weight(1f),
                            contentAlignment = Alignment.CenterEnd,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary,
                            )
                        }
                    }
                }
                Text(
                    "- Rocket: ${item.rocket?.rocket?.rocketFragment?.name.orEmpty()}",
                    modifier = Modifier.placeholder(placeholder),
                )
                Text(
                    "- Company: ${item.rocket?.rocket?.rocketFragment?.company.orEmpty()}",
                    modifier = Modifier.placeholder(placeholder),
                )
                Text(
                    "- Date: ${item.launch_date_local?.toLocalDateTime(TimeZone.UTC)}",
                    modifier = Modifier.placeholder(placeholder),
                )
            }
        }
    }
}
