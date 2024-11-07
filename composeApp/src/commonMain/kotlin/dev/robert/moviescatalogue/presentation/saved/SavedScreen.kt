package dev.robert.moviescatalogue.presentation.saved

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.utils.createImageUrl
import dev.robert.moviescatalogue.presentation.components.NetworkImage
import dev.robert.moviescatalogue.presentation.components.SavedMovieItem
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SavedScreen(
    viewModel: SavedMoviesViewModel = koinViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToDetails: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val savedMovies by viewModel.savedMovies.collectAsStateWithLifecycle()
    SavedScreenContent(
        savedMovies = savedMovies,
        animatedVisibilityScope = animatedVisibilityScope,
        modifier = modifier,
        onEvent = viewModel::onEvent,
        onClick = onNavigateToDetails
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SavedScreenContent(
    savedMovies: List<Movie>,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onEvent: (SavedMoviesEvents) -> Unit,
    onClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val category = listOf("All", "Movies", "Tv Shows")
    var selectedCategory by remember { mutableStateOf(0) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = "Saved Movies",
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp)
            ) {
                category.forEachIndexed { index, item ->
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(
                                color = if (selectedCategory == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                            ).clickable {
                                selectedCategory = index
                                onEvent(SavedMoviesEvents.FilterByType(item))
                            }.padding(8.dp)
                    )
                }
            }
            if (savedMovies.isEmpty())
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Saved movies",
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (selectedCategory == 0) "No saved movies" else "No saved ${category[selectedCategory].lowercase()}",
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            else LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(4.dp),
            ) {
                items(
                    count = savedMovies.size,
                    key = { index -> savedMovies[index].id }
                ) { index ->
                    val movie = savedMovies[index]
                    SavedMovieItem(
                        movie = movie,
                        animatedVisibilityScope = animatedVisibilityScope,
                        onRemove = { onEvent(SavedMoviesEvents.RemoveMovie(it.id)) },
                        onMovieClick = onClick,
                        transitionKey = movie.posterPath +"/" + movie.id
                    )
                }
            }

        }

    }
}
