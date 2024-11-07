package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class GetMovieDetails(
    private val repository: MoviesRepository
) {
    operator fun invoke(movieId: Int, isMovie: Boolean) = repository.getMovieDetails(movieId = movieId, isMovie = isMovie)
}