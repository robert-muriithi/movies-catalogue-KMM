package dev.robert.moviescatalogue.domain.usecase

import dev.robert.moviescatalogue.domain.repository.MoviesRepository

class TodayTrendingMovies(
    private val repository: MoviesRepository
) {
    operator fun invoke() = repository.getTodayTrendingMovies()
}