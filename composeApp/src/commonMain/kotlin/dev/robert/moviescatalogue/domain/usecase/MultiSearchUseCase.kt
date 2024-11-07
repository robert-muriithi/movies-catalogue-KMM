package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class MultiSearchUseCase(
    private val repository: MoviesRepository
) {
    operator fun invoke(query: String) = repository.multiSearch(query)
}