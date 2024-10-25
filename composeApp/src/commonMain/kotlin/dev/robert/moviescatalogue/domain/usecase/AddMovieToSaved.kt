package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class AddMovieToSaved(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie) = repository.addMovieToWatchlist(movie)
}