package dev.robert.moviescatalogue.di

import dev.robert.moviescatalogue.presentation.home.HomeScreenViewModel
import dev.robert.moviescatalogue.presentation.movies.MoviesScreenViewModel
import dev.robert.moviescatalogue.presentation.tvshows.ShowsScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::MoviesScreenViewModel)
    viewModelOf(::ShowsScreenViewModel)
}