package dev.robert.moviescatalogue.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import dev.robert.moviescatalogue.domain.usecase.NowPlayingMovies
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MoviesScreenViewModel(
    nowPlayingMovies: NowPlayingMovies
) : ViewModel() {

    val nowPlayingMovies = nowPlayingMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
}