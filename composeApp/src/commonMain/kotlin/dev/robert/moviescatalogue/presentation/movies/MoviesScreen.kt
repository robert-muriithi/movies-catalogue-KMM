package dev.robert.moviescatalogue.presentation.movies

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun SharedTransitionScope.MoviesScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onNavigateToDetails: (Movie) -> Unit,
    viewModel: MoviesScreenViewModel = koinViewModel()
) {
    val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()
    MoviesScreenContent(
        nowPlayingMovies = nowPlayingMovies,
        popularMovies = popularMovies,
        trendingMovies = trendingMovies,
        onShowClick = onNavigateToDetails,
        animatedVisibilityScope = animatedVisibilityScope,
        modifier = modifier
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MoviesScreenContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    nowPlayingMovies: LazyPagingItems<Movie>,
    popularMovies: LazyPagingItems<Movie>,
    trendingMovies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onShowClick: (Movie) -> Unit
) {
//    val string = R.string.app_name
    val categories = listOf(
        "Now Playing" to nowPlayingMovies,
        "Popular Movies" to popularMovies,
        "Trending Movies" to trendingMovies
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

