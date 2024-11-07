package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class GetMovieReviews(
    private val repository: MoviesRepository
) {
    operator fun invoke(movieId: Int, isMovie: Boolean) = repository.getMovieReviews(movieId = movieId, isMovie = isMovie)
}