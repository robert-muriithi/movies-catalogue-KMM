package dev.robert.moviescatalogue.presentation.similar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.usecase.GetSimilarMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SimilarScreenViewModel(
    private val getSimilarMovies: GetSimilarMovies,
) : ViewModel() {

    private val _similarMovies: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(PagingData.empty())

    val similarMovies = _similarMovies
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )



    fun fetchSimilar(movieId: Int, mediaType: String) {
        val isMovie = mediaType == "movie"
        viewModelScope.launch {
            getSimilarMovies(movieId = movieId, isMovie = isMovie).collectLatest { similarMovies ->
                _similarMovies.update { similarMovies }
            }
        }
    }
}