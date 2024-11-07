package dev.robert.moviescatalogue.presentation.reviews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.robert.moviescatalogue.domain.model.MovieReview
import dev.robert.moviescatalogue.domain.utils.JsonConverter
import dev.robert.moviescatalogue.presentation.components.ReviewItem

@Composable
fun ReviewsScreen(
    reviewJson: String,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val reviews = JsonConverter.fromJsonString<List<MovieReview>>(reviewJson)
    ReviewScreenContent(reviews = reviews, modifier = modifier, onNavigateUp = onNavigateUp)
}

@Composable
fun ReviewScreenContent(
    reviews: List<MovieReview>,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateUp,
                    content = {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                )
                Text(
                    text = "Reviews",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        items(items = reviews) { review ->
            ReviewItem(
                review = review
            )
        }
    }

}