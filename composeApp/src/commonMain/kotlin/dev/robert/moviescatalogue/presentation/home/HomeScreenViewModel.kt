package dev.robert.moviescatalogue.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
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
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val topRatedTvSeries = topRatedTvSeries()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val topRatedMovies = topRatedMovies()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val upcomingMovies = upcomingMovies()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

}




