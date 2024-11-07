package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class SavedStatus(
    private val repository: MoviesRepository
) {
    operator fun invoke(movieId: Int) = repository.isSaved(movieId)
}