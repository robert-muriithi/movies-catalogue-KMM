package dev.robert.moviescatalogue.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.robert.moviescatalogue.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SavedMoviesViewModel(
    repository: MoviesRepository
) : ViewModel() {

    val savedMovies = repository.getSavedMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

}
