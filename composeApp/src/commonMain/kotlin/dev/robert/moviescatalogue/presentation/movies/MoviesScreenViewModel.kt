package dev.robert.moviescatalogue.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import dev.robert.moviescatalogue.domain.usecase.GetPopularMovies
import dev.robert.moviescatalogue.domain.usecase.NowPlayingMovies
import dev.robert.moviescatalogue.domain.usecase.TodayTrendingMovies
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MoviesScreenViewModel(
    nowPlayingMovies: NowPlayingMovies,
    trendingMovies: TodayTrendingMovies,
    popularMovies: GetPopularMovies
) : ViewModel() {

    val nowPlayingMovies = nowPlayingMovies()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val popularMovies = popularMovies()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val trendingMovies = trendingMovies()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

}