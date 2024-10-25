package dev.robert.moviescatalogue.presentation.saved

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
import dev.robert.moviescatalogue.presentation.components.MovieItem
import dev.robert.moviescatalogue.presentation.components.NetworkImage
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SavedScreen(
    viewModel: SavedMoviesViewModel = koinViewModel()
) {
    val savedMovies by viewModel.savedMovies.collectAsStateWithLifecycle()
    SavedScreenContent(savedMovies)
}

@Composable
fun SavedScreenContent(
    savedMovies: List<Movie>
) {
    LazyColumn {
        items(savedMovies.size) { index ->
            val movie = savedMovies[index]
            SavedMovieItem(movie = movie)
        }
    }
}
@Composable
fun SavedMovieItem(
    movie: Movie
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable {

            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            NetworkImage(
                imageUrl = movie.posterPath.createImageUrl(),
                contentDescription = "Movie",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = movie.title.ifEmpty { movie.name },
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = movie.releaseDate.ifEmpty { movie.firstAirDate },
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
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
                        text = movie.voteAverage.toString(),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.overview,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}