package dev.robert.moviescatalogue.presentation.tvshows

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.presentation.components.MovieItem
import dev.robert.moviescatalogue.presentation.components.PagingColumnUi
import org.koin.compose.koinInject

@Composable
fun ShowsScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Movie) -> Unit,
    viewModel: ShowsScreenViewModel = koinInject()
) {
    val shows = viewModel.trendingTvSeries.collectAsLazyPagingItems()
    ShowScreenContent(
        shows = shows,
        onShowClick = onNavigateToDetails
    )
}

@Composable
fun ShowScreenContent(
    shows: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onShowClick: (Movie) -> Unit
) {

    PagingColumnUi(
        title = "Trending TV Shows",
        data = shows,
        content = { show ->
            MovieItem(movie = show, onMovieClick = onShowClick)
        }
    )

}