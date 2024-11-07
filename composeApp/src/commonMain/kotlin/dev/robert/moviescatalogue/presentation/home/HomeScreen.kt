package dev.robert.moviescatalogue.presentation.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import dev.robert.moviescatalogue.domain.model.Movie
import org.koin.compose.viewmodel.koinViewModel
import dev.robert.moviescatalogue.presentation.components.TrendingMoviesHorizontalPager
import dev.robert.moviescatalogue.presentation.components.MovieItem
import dev.robert.moviescatalogue.presentation.components.PagingRowUi

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToMovieDetails: (Movie) -> Unit,
    onNavigateToViewAll: (String) -> Unit,
    onNavigateToSearch: () -> Unit
) {
    val viewModel = koinViewModel<HomeScreenViewModel>()
    val weeklyTrending = viewModel.weeklyTrending.collectAsLazyPagingItems()
    val topRatedTvSeries = viewModel.topRatedTvSeries.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMovies.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.collectAsLazyPagingItems()


    HomeScreenContent(
        discover = weeklyTrending,
        topRatedMovies = topRatedMovies,
        topRatedTvSeries = topRatedTvSeries,
        upcomingMovies = upcomingMovies,
        onMovieClick = onNavigateToMovieDetails,
        onNavigateToViewAll = onNavigateToViewAll,
        onNavigateToSearch = onNavigateToSearch,
        animatedVisibilityScope = animatedVisibilityScope
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreenContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    discover: LazyPagingItems<Movie>,
    topRatedMovies: LazyPagingItems<Movie>,
    topRatedTvSeries: LazyPagingItems<Movie>,
    upcomingMovies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onMovieClick: (Movie) -> Unit,
    onNavigateToViewAll: (String) -> Unit,
    onNavigateToSearch: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movies Catalogue - KMM")
                },
                actions = {
                    IconButton(onClick = onNavigateToSearch, content = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    })
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = modifier.fillMaxSize().padding(paddingValues)) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    Text(
                        text = "Weekly Trending Movies",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    TrendingMoviesHorizontalPager(
                        weeksTrending = discover,
                        onMovieClick = onMovieClick,
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Top Rated Movies",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        TextButton(
                            onClick = {
                                onNavigateToViewAll("top_rated_movies")
                            },
                            content = {
                                Text(
                                    text = "View All",
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Thin
                                    )
                                )
                            }
                        )
                    }
                    PagingRowUi(
                        data = topRatedMovies,
                        content = { movie ->
                            MovieItem(movie = movie, onMovieClick = onMovieClick, animatedVisibilityScope = animatedVisibilityScope, sharedTransitionKey = movie.posterPath +"/" + movie.id)
                        }
                    )
                }

            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Top Rated TV Series",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        TextButton(
                            onClick = {
                                onNavigateToViewAll("top_rated_tv_series")
                            },
                            content = {
                                Text(
                                    text = "View All",
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Thin
                                    )
                                )
                            }
                        )
                    }
                    PagingRowUi(
                        data = topRatedTvSeries,
                        content = { movie ->
                            MovieItem(movie = movie, onMovieClick = onMovieClick, animatedVisibilityScope = animatedVisibilityScope, sharedTransitionKey = movie.posterPath +"/" + movie.id)
                        }
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Upcoming Movies",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                        TextButton(
                            onClick = {
                                onNavigateToViewAll("upcoming_movies")
                            },
                            content = {
                                Text(
                                    text = "View All",
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Thin
                                    )
                                )
                            }
                        )
                    }
                    PagingRowUi(
                        data = upcomingMovies,
                        content = { movie ->
                            MovieItem(movie = movie, onMovieClick = onMovieClick, animatedVisibilityScope = animatedVisibilityScope, sharedTransitionKey = movie.posterPath +"/" + movie.id)
                        }
                    )
                }
            }
        }
    }
}



