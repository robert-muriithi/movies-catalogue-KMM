package dev.robert.moviescatalogue

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
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
import dev.robert.moviescatalogue.presentation.movie_details.MovieDetailsScreen
import dev.robert.moviescatalogue.presentation.movies.MoviesScreen
import dev.robert.moviescatalogue.presentation.navigation.BottomNavItem
import dev.robert.moviescatalogue.presentation.navigation.Destination
import dev.robert.moviescatalogue.presentation.saved.SavedScreen
import dev.robert.moviescatalogue.presentation.settings.SettingsScreen
import dev.robert.moviescatalogue.presentation.tvshows.ShowsScreen
import dev.robert.moviescatalogue.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import org.koin.dsl.module

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    AppTheme {
        KoinContext {
            setSingletonImageLoaderFactory { context ->
                getAsyncImageLoader(context)
            }
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            var showAppBar by rememberSaveable { mutableStateOf(true) }

            navBackStackEntry?.destination?.let { currentDestination ->
                showAppBar = showBottomBar(currentDestination)
            }

            Scaffold(
                bottomBar = {
                    AnimatedVisibility(
                        visible = showAppBar,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        BottomNavigationBar(navController)
                    }
                }
            ) { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = Destination.HomeScreen,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    mainNavGraph(navController)
                }
            }
        }
    }
}


fun showBottomBar(currentDestination: NavDestination?): Boolean {
    return listOf(
        Destination.HomeScreen,
        Destination.MoviesScreen,
        Destination.TvShowsScreen,
        Destination.SavedScreen,
        Destination.SettingsScreen
    ).any { currentDestination?.hasRoute(it::class) == true }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    composable<Destination.HomeScreen> {
        HomeScreen(navController, onNavigateToMovieDetails = { movie ->
            val obj = JsonConverter.toJsonString(movie, Movie.serializer())
            navController.navigate(
                Destination.MovieDetailsScreen(
                    movie = obj
                )
            )
        })
    }
    composable<Destination.MoviesScreen> {
        MoviesScreen(
            onNavigateToDetails = { movie ->
                val obj = JsonConverter.toJsonString(movie, Movie.serializer())
                navController.navigate(Destination.MovieDetailsScreen(movie = obj))
            }
        )
    }

    composable<Destination.TvShowsScreen> {
        ShowsScreen(
            onNavigateToDetails = { movie ->
                val obj = JsonConverter.toJsonString(movie, Movie.serializer())
                navController.navigate(Destination.MovieDetailsScreen(movie = obj))
            }
        )
    }

    composable<Destination.MovieDetailsScreen> {
        val movie = it.toRoute<Destination.MovieDetailsScreen>()
        MovieDetailsScreen(movie = movie.movie, navController = navController)
    }

    composable<Destination.SearchScreen> {

    }
    composable<Destination.SavedScreen> {
        SavedScreen()
    }

    composable<Destination.SettingsScreen> {
        SettingsScreen()
    }

}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
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
}

@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}

