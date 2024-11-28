package dev.robert.moviescatalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.robert.moviescatalogue.data.datastore.MoviesCataloguePrefs
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    prefs: MoviesCataloguePrefs
) : ViewModel() {

    val theme = prefs.darkTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )
}