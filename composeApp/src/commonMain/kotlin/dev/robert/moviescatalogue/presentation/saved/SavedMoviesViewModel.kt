package dev.robert.moviescatalogue.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedMoviesViewModel(
    private val repository: MoviesRepository
) : ViewModel() {
    private val _currentFilter = MutableStateFlow("All")

    val savedMovies = combine(
        repository.getSavedMovies(),
        _currentFilter
    ) { movies, filter ->
        when(filter) {
            "All" -> movies
            "Movies" -> movies.filter { it.mediaType == "movie" }
            "Tv Shows" -> movies.filter { it.mediaType == "tv" }
            else -> movies
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun onEvent(event: SavedMoviesEvents) {
        when (event) {
            is SavedMoviesEvents.RemoveMovie -> removeMovie(event.movieId)
            is SavedMoviesEvents.FilterByType -> filterByType(event.type)
        }
    }

    private fun removeMovie(movieId: Int) {
        viewModelScope.launch {
            repository.removeMovieFromWatchlist(movieId)
        }
    }

    private fun filterByType(type: String) {
        _currentFilter.value = type
    }
}

sealed class SavedMoviesEvents {
    data class RemoveMovie(val movieId: Int) : SavedMoviesEvents()
    data class FilterByType(val type: String) : SavedMoviesEvents()
}

