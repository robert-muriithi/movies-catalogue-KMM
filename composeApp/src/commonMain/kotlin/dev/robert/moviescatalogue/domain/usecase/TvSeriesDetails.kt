package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class TvSeriesDetails(
    private val repository: MoviesRepository
) {
    operator fun invoke(tvSeriesId: Int) = repository.getTvSeriesDetails(tvSeriesId)
}