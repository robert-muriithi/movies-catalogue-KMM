package dev.robert.moviescatalogue.data.api.pager

import dev.robert.moviescatalogue.data.api.ApiService
import dev.robert.moviescatalogue.data.api.dto.MovieResponse

class DiscoverMoviesPagingSource(
    private val apiService: ApiService
) : BasePagingSource<MovieResponse>(
    fetchMovies = { page -> apiService.getDiscoverMovies(page) }
)