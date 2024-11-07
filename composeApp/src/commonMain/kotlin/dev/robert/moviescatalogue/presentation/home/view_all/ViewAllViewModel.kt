package dev.robert.moviescatalogue.presentation.home.view_all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import dev.robert.moviescatalogue.domain.usecase.TopRatedMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedTvSeries
import dev.robert.moviescatalogue.domain.usecase.UpcomingMovies
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ViewAllViewModel(
    topRatedTvSeries: TopRatedTvSeries,
    topRatedMovies: TopRatedMovies,
    upcomingMovies: UpcomingMovies,
) : ViewModel() {

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