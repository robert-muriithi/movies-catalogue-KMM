package dev.robert.moviescatalogue.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesCataloguePrefs(private val dataStore: DataStore<Preferences>) {

    companion object {
        private const val KEY_DARK_THEME = "dark_theme"
    }

    val darkTheme: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[booleanPreferencesKey(KEY_DARK_THEME)] ?: false
        }

    suspend fun setDarkTheme(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(KEY_DARK_THEME)] = isDarkTheme
        }
    }
}