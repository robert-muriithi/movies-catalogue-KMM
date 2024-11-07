package dev.robert.moviescatalogue.presentation.settings

import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

}

data class SettingsState(
    val selectedTheme : Int = 0
)