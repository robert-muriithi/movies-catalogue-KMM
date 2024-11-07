package dev.robert.moviescatalogue.presentation.home.view_all

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.presentation.components.MovieItem
import dev.robert.moviescatalogue.presentation.components.PagingColumnUi
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ViewAllScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    type: String,
    onClick: (Movie) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<ViewAllViewModel>()

    val (movies, title) = when (type) {
        "top_rated_tv_series" -> viewModel.topRatedTvSeries.collectAsLazyPagingItems() to "Top Rated TV Series"
        "top_rated_movies" -> viewModel.topRatedMovies.collectAsLazyPagingItems() to "Top Rated Movies"
        "upcoming_movies" -> viewModel.upcomingMovies.collectAsLazyPagingItems() to "Upcoming Movies"
        else -> throw IllegalArgumentException("Invalid type")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp,
                        content = {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    )
                }
            )
        },
        modifier = modifier
            .padding(top = 4.dp)
    ) { paddingValues ->
        ViewAllScreenContent(
            movies = movies,
            onClick = onClick,
            animatedVisibilityScope = animatedVisibilityScope,
            modifier = Modifier.padding(paddingValues)
        )
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ViewAllScreenContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    movies: LazyPagingItems<Movie>,
    onClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    PagingColumnUi(
        data = movies,
        content = { movie ->
            MovieItem(movie = movie, onMovieClick = onClick, sharedTransitionKey = movie.posterPath +"/" + movie.id, animatedVisibilityScope = animatedVisibilityScope)
        },
        modifier = modifier
    )
}