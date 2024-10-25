package dev.robert.moviescatalogue.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.usecase.GetTrendingUseCase
import dev.robert.moviescatalogue.domain.usecase.TopRatedMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedTvSeries
import dev.robert.moviescatalogue.domain.usecase.UpcomingMovies
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(
    topRatedTvSeries: TopRatedTvSeries,
    topRatedMovies: TopRatedMovies,
    upcomingMovies: UpcomingMovies,
    trendingUseCase: GetTrendingUseCase
) : ViewModel(){

    val weeklyTrending : StateFlow<PagingData<Movie>> = trendingUseCase()
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




