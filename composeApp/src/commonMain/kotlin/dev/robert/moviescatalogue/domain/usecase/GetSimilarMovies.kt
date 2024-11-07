package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class GetSimilarMovies (
    private val repository: MoviesRepository,
) {
    operator fun invoke(movieId: Int, isMovie: Boolean) = repository.getSimilarMovies(movieId = movieId, isMovie = isMovie)
}