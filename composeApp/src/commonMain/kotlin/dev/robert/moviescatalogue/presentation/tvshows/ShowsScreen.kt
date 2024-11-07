package dev.robert.moviescatalogue.presentation.tvshows

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.presentation.components.CategoryItem
import dev.robert.moviescatalogue.presentation.components.MovieItem
import dev.robert.moviescatalogue.presentation.components.PagingColumnUi
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ShowsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Movie) -> Unit,
    viewModel: ShowsScreenViewModel = koinViewModel()
) {
    val trendingShows = viewModel.trendingTvSeries.collectAsLazyPagingItems()
    val airingToday = viewModel.airingToday.collectAsLazyPagingItems()
    val popularSeries = viewModel.popularSeries.collectAsLazyPagingItems()
    ShowScreenContent(
        trendingShows = trendingShows,
        airingToday = airingToday,
        popularSeries = popularSeries,
        onShowClick = onNavigateToDetails,
        animatedVisibilityScope = animatedVisibilityScope,
        modifier = modifier
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ShowScreenContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    trendingShows: LazyPagingItems<Movie>,
    airingToday: LazyPagingItems<Movie>,
    popularSeries: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onShowClick: (Movie) -> Unit
) {
    val categories = listOf(
        "Trending Series" to trendingShows,
        "Airing Today" to airingToday,
        "Popular Series" to popularSeries
    )

    var selectedCategory by remember { mutableStateOf(categories.first()) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(categories.size) { index ->
                val (category, _) = categories[index]
                CategoryItem(
                    category = category,
                    isSelected = category == selectedCategory.first,
                    onCategorySelected = { selectedCategory = categories[index] }
                )
            }
        }
        PagingColumnUi(
            data = selectedCategory.second,
            content = { movie ->
                MovieItem(movie = movie, onMovieClick = onShowClick, animatedVisibilityScope = animatedVisibilityScope, sharedTransitionKey = movie.posterPath +"/" + movie.id)
            }
        )
    }

}