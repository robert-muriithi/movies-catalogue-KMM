package dev.robert.moviescatalogue.data.api.pager

import dev.robert.moviescatalogue.data.api.ApiService
import dev.robert.moviescatalogue.data.api.dto.MovieResponse

class TrendingTodayMoviesPagingSource(
    private val apiService: ApiService
) : BasePagingSource<MovieResponse>(
    fetchMovies = { page ->
        apiService.getTrendingTodayMovies(page)
    }
)