package dev.robert.moviescatalogue.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.usecase.GetDiscoverMovies
import dev.robert.moviescatalogue.domain.usecase.GetDiscoverSeries
import dev.robert.moviescatalogue.domain.usecase.TopRatedMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedTvSeries
import dev.robert.moviescatalogue.domain.usecase.UpcomingMovies
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(
    discoverMovies: GetDiscoverMovies,
    discoverSeries: GetDiscoverSeries,
    topRatedTvSeries: TopRatedTvSeries,
    topRatedMovies: TopRatedMovies,
    upcomingMovies: UpcomingMovies
) : ViewModel(){

    val discoverMovies : StateFlow<PagingData<Movie>> = discoverMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val discoverSeries : StateFlow<PagingData<Movie>> = discoverSeries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )




    val topRatedTvSeries = topRatedTvSeries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val topRatedMovies = topRatedMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val upcomingMovies = upcomingMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

}




