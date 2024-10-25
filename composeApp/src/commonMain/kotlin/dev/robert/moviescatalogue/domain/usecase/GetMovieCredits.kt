package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class GetMovieCredits(
    private val repository: MoviesRepository
) {
    operator fun invoke(movieId: Int) = repository.getMovieCredits(movieId)
}