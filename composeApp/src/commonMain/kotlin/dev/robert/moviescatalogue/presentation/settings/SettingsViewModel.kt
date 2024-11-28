package dev.robert.moviescatalogue.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.robert.moviescatalogue.data.datastore.MoviesCataloguePrefs
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val prefs: MoviesCataloguePrefs
) : ViewModel() {

    val theme : StateFlow<Boolean> = prefs.darkTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )

    fun onThemeChange(isDarkTheme: Boolean) {
        viewModelScope.launch {
            prefs.setDarkTheme(isDarkTheme)
        }
    }
}

data class SettingsState(
    val selectedTheme : Int = 0
)