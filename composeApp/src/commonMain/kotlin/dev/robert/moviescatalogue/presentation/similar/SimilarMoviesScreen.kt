package dev.robert.moviescatalogue.presentation.similar

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.presentation.components.MovieItem
import dev.robert.moviescatalogue.presentation.components.PagingColumnUi
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SimilarMoviesScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onNavigateToMovieDetails: (Movie) -> Unit,
    onNavigateBack: () -> Unit,
    movieId: Int,
    mediaType: String,
    viewModel: SimilarScreenViewModel = koinViewModel()
) {
    val movies = viewModel.similarMovies.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchSimilar(movieId = movieId, mediaType = mediaType)
    }

    SimilarMoviesContent(
        movies = movies,
        modifier = modifier,
        onClick = onNavigateToMovieDetails,
        animatedVisibilityScope = animatedVisibilityScope,
        navigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.SimilarMoviesContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    movies: LazyPagingItems<Movie>,
    onClick: (Movie) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Similar") },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack,
                        content = {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    )
                }
            )
        },
        modifier = modifier
            .padding(top = 4.dp),
        content = { paddingValues ->
            PagingColumnUi(
                data = movies,
                content = { movie ->
                    MovieItem(
                        movie = movie,
                        onMovieClick = onClick,
                        sharedTransitionKey = movie.posterPath + "/" + movie.id,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                },
                modifier = Modifier.padding(paddingValues)
            )
        }
    )

}