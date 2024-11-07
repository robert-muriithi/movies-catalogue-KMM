package dev.robert.moviescatalogue.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.robert.moviescatalogue.domain.model.MovieReview
import dev.robert.moviescatalogue.domain.utils.formatDate

@Composable
fun ReviewItem(
    review: MovieReview?,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val isCollapsed = remember { mutableStateOf(true) }

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface),
        onClick = {
            review?.url?.let { url ->
                uriHandler.openUri(url)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "Review By ${review?.author}",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review?.content ?: "No content",
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = if (isCollapsed.value) 3 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = {
                        isCollapsed.value = !isCollapsed.value
                    },
                    content = {
                        Text(
                            text = if (isCollapsed.value) "Read More" else "Read Less",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Thin
                        )
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Text(
                        text = "Created At: ${formatDate(review?.createdAt)}",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 10.sp
                    )
                    Text(
                        text = "Updated At: ${formatDate(review?.updatedAt)}",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}