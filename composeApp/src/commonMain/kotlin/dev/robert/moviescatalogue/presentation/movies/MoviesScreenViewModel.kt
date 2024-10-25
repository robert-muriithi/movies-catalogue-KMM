package dev.robert.moviescatalogue.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import dev.robert.moviescatalogue.domain.usecase.GetPopularMovies
import dev.robert.moviescatalogue.domain.usecase.NowPlayingMovies
import dev.robert.moviescatalogue.domain.usecase.TodayTrendingMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedMovies
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MoviesScreenViewModel(
    nowPlayingMovies: NowPlayingMovies,
    trendingMovies: TodayTrendingMovies,
    popularMovies: GetPopularMovies
) : ViewModel() {

    val nowPlayingMovies = nowPlayingMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val popularMovies = popularMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val trendingMovies = trendingMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

}