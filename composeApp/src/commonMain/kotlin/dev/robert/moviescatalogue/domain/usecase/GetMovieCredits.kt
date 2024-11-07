package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class GetMovieCredits(
    private val repository: MoviesRepository
) {
    operator fun invoke(movieId: Int, isMovie: Boolean) = repository.getMovieCredits(movieId = movieId, isMovie = isMovie)
}