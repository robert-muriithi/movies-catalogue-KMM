package dev.robert.moviescatalogue.presentation.home

import dev.robert.moviescatalogue.domain.model.Movie

data class HomeScreenState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)