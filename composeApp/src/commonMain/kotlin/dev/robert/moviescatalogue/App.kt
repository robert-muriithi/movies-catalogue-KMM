package dev.robert.moviescatalogue

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.utils.JsonConverter
import dev.robert.moviescatalogue.presentation.home.HomeScreen
import dev.robert.moviescatalogue.presentation.home.view_all.ViewAllScreen
import dev.robert.moviescatalogue.presentation.movie_details.MovieDetailsScreen
import dev.robert.moviescatalogue.presentation.movies.MoviesScreen
import dev.robert.moviescatalogue.presentation.navigation.BottomNavItem
import dev.robert.moviescatalogue.presentation.navigation.Destination
import dev.robert.moviescatalogue.presentation.reviews.ReviewsScreen
import dev.robert.moviescatalogue.presentation.saved.SavedScreen
import dev.robert.moviescatalogue.presentation.search.SearchScreen
import dev.robert.moviescatalogue.presentation.settings.SettingsScreen
import dev.robert.moviescatalogue.presentation.similar.SimilarMoviesScreen
import dev.robert.moviescatalogue.presentation.tvshows.ShowsScreen
import dev.robert.moviescatalogue.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalCoilApi::class, ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun App(
    viewModel: MainViewModel = koinViewModel(),
) {
    val isDarkTheme by viewModel.theme.collectAsStateWithLifecycle()
    AppTheme() {
        KoinContext {
            setSingletonImageLoaderFactory { context ->
                getAsyncImageLoader(context)
            }
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            var showNavBar by rememberSaveable { mutableStateOf(true) }

            navBackStackEntry?.destination?.let { currentDestination ->
                showNavBar = showBottomBar(currentDestination)
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                Scaffold(
                    bottomBar = {
                        AnimatedVisibility(
                            visible = showNavBar,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it })
                        ) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { innerPadding ->
                    SharedTransitionLayout {
                        NavHost(
                            navController = navController,
                            startDestination = Destination.HomeScreen,
                            modifier = Modifier
                                .padding(bottom = innerPadding.calculateBottomPadding())
                                .consumeWindowInsets(paddingValues = if(showNavBar) PaddingValues(bottom = 0.dp) else innerPadding)
                        ) {
                            composable<Destination.HomeScreen> {
                                HomeScreen(
                                    onNavigateToMovieDetails = { movie ->
                                        val obj =
                                            JsonConverter.toJsonString(movie, Movie.serializer())
                                        navController.navigate(
                                            Destination.MovieDetailsScreen(
                                                movie = obj
                                            )
                                        )
                                    },
                                    onNavigateToViewAll = { type ->
                                        navController.navigate(Destination.ViewAllScreen(type = type))
                                    },
                                    onNavigateToSearch = {
                                        navController.navigate(Destination.SearchScreen)
                                    },
                                    animatedVisibilityScope = this
                                )
                            }
                            composable<Destination.MoviesScreen> {
                                MoviesScreen(
                                    onNavigateToDetails = { movie ->
                                        val obj =
                                            JsonConverter.toJsonString(movie, Movie.serializer())
                                        navController.navigate(Destination.MovieDetailsScreen(movie = obj))
                                    },
                                    animatedVisibilityScope = this,
                                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                                )
                            }

                            composable<Destination.TvShowsScreen> {
                                ShowsScreen(
                                    onNavigateToDetails = { movie ->
                                        val obj =
                                            JsonConverter.toJsonString(movie, Movie.serializer())
                                        navController.navigate(Destination.MovieDetailsScreen(movie = obj))
                                    },
                                    animatedVisibilityScope = this,
                                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                                )
                            }

                            composable<Destination.MovieDetailsScreen> {
                                val movie = it.toRoute<Destination.MovieDetailsScreen>()
                                MovieDetailsScreen(
                                    jsonString = movie.movie,
                                    animatedVisibilityScope = this,
                                    onMovieClick = { movie ->
                                        val movieObj = JsonConverter.toJsonString(movie, Movie.serializer())
                                        navController.navigate(Destination.MovieDetailsScreen(movie = movieObj))
                                    },
                                    onNavigateBack = {
                                        navController.navigateUp()
                                    },
                                    onNavigateToReviews = { reviews ->
                                        navController.navigate(Destination.ReviewsScreen(reviews = reviews))
                                    },
                                    onNavigateToSimilar =  { movieId, mediaType ->
                                        navController.navigate(Destination.SimilarMoviesScreen(movieId = movieId, mediaType = mediaType))
                                    }
                                )
                            }

                            composable<Destination.SearchScreen> {
                                SearchScreen(
                                    onNavigateUp = {
                                        navController.navigateUp()
                                    },
                                    onNavigateToDetails = { movie ->
                                        val movieObj =
                                            JsonConverter.toJsonString(movie, Movie.serializer())
                                        navController.navigate(Destination.MovieDetailsScreen(movie = movieObj))
                                    },
                                    animatedVisibilityScope = this
                                )
                            }
                            composable<Destination.SavedScreen> {
                                SavedScreen(
                                    animatedVisibilityScope = this,
                                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                                    onNavigateToDetails = { movie ->
                                        val movieObj = JsonConverter.toJsonString(movie, Movie.serializer())
                                        navController.navigate(Destination.MovieDetailsScreen(movie = movieObj))
                                    }
                                )
                            }

                            composable<Destination.SettingsScreen> {
                                SettingsScreen(
                                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                                )
                            }

                            composable<Destination.ReviewsScreen> {
                                val reviews = it.toRoute<Destination.ReviewsScreen>()
                                ReviewsScreen(
                                    reviewJson = reviews.reviews,
                                    onNavigateUp = {
                                        navController.navigateUp()
                                    },
                                )
                            }

                            composable<Destination.ViewAllScreen> {
                                val type = it.toRoute<Destination.ViewAllScreen>()
                                ViewAllScreen(
                                    type = type.type,
                                    onClick = { movie: Movie ->
                                        val movieObj =
                                            JsonConverter.toJsonString(movie, Movie.serializer())
                                        navController.navigate(Destination.MovieDetailsScreen(movie = movieObj))
                                    },
                                    onNavigateUp = {
                                        navController.navigateUp()
                                    },
                                    animatedVisibilityScope = this,
                                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                                )
                            }

                            composable<Destination.SimilarMoviesScreen> { navBackStackEntry ->
                                val args = navBackStackEntry.toRoute<Destination.SimilarMoviesScreen>()
                                SimilarMoviesScreen(
                                    animatedVisibilityScope = this,
                                    onNavigateToMovieDetails = { movie ->
                                        val movieObj = JsonConverter.toJsonString(movie, Movie.serializer())
                                        navController.navigate(Destination.MovieDetailsScreen(movie = movieObj))
                                    },
                                    onNavigateBack = {
                                        navController.navigateUp()
                                    },
                                    movieId = args.movieId,
                                    mediaType = args.mediaType,
                                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


fun showBottomBar(currentDestination: NavDestination?): Boolean = listOf(
    Destination.HomeScreen,
    Destination.MoviesScreen,
    Destination.TvShowsScreen,
    Destination.SavedScreen,
    Destination.SettingsScreen
).any { currentDestination?.hasRoute(it::class) == true }


fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()

@Composable
fun BottomNavigationBar(navController: NavController) = NavigationBar(
    contentColor = MaterialTheme.colorScheme.onBackground,
    containerColor = MaterialTheme.colorScheme.background,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Destination.HomeScreen
    BottomNavItem.entries.forEach { item ->
        val isSelected by remember(currentRoute) {
            derivedStateOf { currentRoute == item.route::class.qualifiedName }
        }
        NavigationBarItem(
            selected = isSelected,
            onClick = {
                navController.navigate(item.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(if (isSelected) item.selectedIcon else item.unselectedIcon),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = item.title,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            },
        )
    }
}


@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}

