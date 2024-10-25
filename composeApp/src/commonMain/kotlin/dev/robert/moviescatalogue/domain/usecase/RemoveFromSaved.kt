package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class RemoveFromSaved(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int) = repository.removeMovieFromWatchlist(movieId)
}