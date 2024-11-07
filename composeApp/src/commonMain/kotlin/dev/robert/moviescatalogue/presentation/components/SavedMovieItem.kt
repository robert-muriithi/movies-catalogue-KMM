package dev.robert.moviescatalogue.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.utils.createImageUrl
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SavedMovieItem(
    animatedVisibilityScope: AnimatedVisibilityScope,
    transitionKey : String,
    movie: Movie,
    modifier: Modifier = Modifier,
    onRemove: (Movie) -> Unit = {},
    onMovieClick: (Movie) -> Unit = {}
) {
    SwipeToDeleteContainer(
        item = movie,
        onDelete = onRemove,
        content = { savedMovie ->
            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(120.dp)
                    .padding(8.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .clickable {
                        onMovieClick(savedMovie)
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NetworkImage(
                        imageUrl = savedMovie.posterPath.createImageUrl(),
                        contentDescription = "Movie",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxHeight()
                            .sharedElement(
                                state = rememberSharedContentState(
                                    key = transitionKey
                                ),
                                animatedVisibilityScope = animatedVisibilityScope,
                            )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = savedMovie.title.ifEmpty { savedMovie.name } + " (${savedMovie.mediaType})",
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = savedMovie.releaseDate.ifEmpty { savedMovie.firstAirDate },
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Thin
                            )
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(11.dp)
                            )
                            Text(
                                text = savedMovie.voteAverage.toString(),
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = savedMovie.overview,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            maxLines = 3,
                            modifier = Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                }
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == StartToEnd) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DismissBackground(dismissState = state)
            },
            content = { content(item) },
            enableDismissFromEndToStart = false,
        )
    }
}