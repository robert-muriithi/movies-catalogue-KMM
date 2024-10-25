package dev.robert.moviescatalogue.presentation.navigation

import kotlinx.serialization.Serializable
import movies_catalogue.composeapp.generated.resources.Res
import movies_catalogue.composeapp.generated.resources.ic_bookmark_boarder
import movies_catalogue.composeapp.generated.resources.ic_bookmark_filled
import movies_catalogue.composeapp.generated.resources.ic_folder_filled
import movies_catalogue.composeapp.generated.resources.ic_folder_outline
import movies_catalogue.composeapp.generated.resources.ic_home
import movies_catalogue.composeapp.generated.resources.ic_play_filled
import movies_catalogue.composeapp.generated.resources.ic_play_outline
import movies_catalogue.composeapp.generated.resources.ic_settings
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
}


enum class BottomNavItem(
    val title: String,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
    val route: Destination
) {
     Home(
         title = "Home",
         selectedIcon = Res.drawable.ic_home,
         unselectedIcon = Res.drawable.ic_home,
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
        selectedIcon = Res.drawable.ic_bookmark_filled,
        unselectedIcon = Res.drawable.ic_bookmark_boarder,
        route = Destination.SavedScreen
    ),
    Settings("Settings",
        selectedIcon = Res.drawable.ic_settings,
        unselectedIcon = Res.drawable.ic_settings,
        route = Destination.SettingsScreen
    )
}