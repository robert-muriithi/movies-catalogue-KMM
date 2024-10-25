package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class GetPopularSeries(
    private val repository: MoviesRepository
) {
    operator fun invoke() = repository.getPopularTvSeries()
}