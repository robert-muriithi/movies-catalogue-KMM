package dev.robert.moviescatalogue.data.pager

import dev.robert.moviescatalogue.data.ApiService
import dev.robert.moviescatalogue.data.dto.MovieResponse
import dev.robert.moviescatalogue.data.dto.MoviesResponse

class TrendingPagingSource (
    val apiService: ApiService
) : BasePagingSource<MovieResponse, MoviesResponse>(
    fetchItems = { page ->
        apiService.getAllTrending(page = page)
    }
)