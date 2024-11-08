package dev.robert.moviescatalogue.presentation.navigation

import kotlinx.serialization.Serializable
import movies_catalogue.composeapp.generated.resources.Res
import movies_catalogue.composeapp.generated.resources.bookmark_outline
import movies_catalogue.composeapp.generated.resources.bookmark_filled
import movies_catalogue.composeapp.generated.resources.ic_folder_filled
import movies_catalogue.composeapp.generated.resources.ic_folder_outline
import movies_catalogue.composeapp.generated.resources.home_filled
import movies_catalogue.composeapp.generated.resources.home_outline
import movies_catalogue.composeapp.generated.resources.ic_play_filled
import movies_catalogue.composeapp.generated.resources.ic_play_outline
import movies_catalogue.composeapp.generated.resources.settings_filled
import movies_catalogue.composeapp.generated.resources.settings_outline
import org.jetbrains.compose.resources.DrawableResource


@Serializable
sealed class Destination {
    @Serializable
    data object HomeScreen : Destination()
    @Serializable
    data class MovieDetailsScreen(val movie: String) : Destination()
    @Serializable
    data object MoviesScreen : Destination()
    @Serializable
    data object TvShowsScreen : Destination()
    @Serializable
    data object SavedScreen : Destination()
    @Serializable
    data object SearchScreen : Destination()
    @Serializable
    data object SettingsScreen : Destination()
    @Serializable
    data class ViewAllScreen(val type: String) : Destination()
    @Serializable
    data class ReviewsScreen(val reviews: String) : Destination()
    @Serializable
    data class SimilarMoviesScreen(val movieId: Int, val mediaType: String) : Destination()
}


enum class BottomNavItem(
    val title: String,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
    val route: Destination
) {
     Home(
         title = "Home",
         selectedIcon = Res.drawable.home_filled,
         unselectedIcon = Res.drawable.home_outline,
         route = Destination.HomeScreen
     ),
     Movies("Movies",
         selectedIcon = Res.drawable.ic_play_filled,
         unselectedIcon = Res.drawable.ic_play_outline,
         route = Destination.MoviesScreen
     ),
    TvShows("Tv Shows",
        selectedIcon = Res.drawable.ic_folder_filled,
        unselectedIcon = Res.drawable.ic_folder_outline,
        route = Destination.TvShowsScreen
    ),
    Saved(
        title = "Saved",
        selectedIcon = Res.drawable.bookmark_filled,
        unselectedIcon = Res.drawable.bookmark_outline,
        route = Destination.SavedScreen
    ),
    Settings("Settings",
        selectedIcon = Res.drawable.settings_filled,
        unselectedIcon = Res.drawable.settings_outline,
        route = Destination.SettingsScreen
    )
}