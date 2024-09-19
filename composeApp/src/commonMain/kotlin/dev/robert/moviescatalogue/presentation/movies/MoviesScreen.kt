package dev.robert.moviescatalogue.presentation.movies

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.presentation.components.MovieItem
import dev.robert.moviescatalogue.presentation.components.PagingColumnUi
import org.koin.compose.koinInject

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Movie) -> Unit,
    viewModel: MoviesScreenViewModel = koinInject()
) {
    val movies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    MoviesScreenContent(
        shows = movies,
        onShowClick = onNavigateToDetails
    )
}

@Composable
fun MoviesScreenContent(
    shows: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onShowClick: (Movie) -> Unit
) {
    PagingColumnUi(
        title = "Now Playing Movies",
        data = shows,
        content = { show ->
            MovieItem(movie = show, onMovieClick = onShowClick)
        }
    )
}