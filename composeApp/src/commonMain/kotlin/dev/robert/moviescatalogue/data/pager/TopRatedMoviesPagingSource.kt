package dev.robert.moviescatalogue.data.pager

import dev.robert.moviescatalogue.data.ApiService
import dev.robert.moviescatalogue.data.dto.MovieResponse
import dev.robert.moviescatalogue.data.dto.MoviesResponse

class TopRatedMoviesPagingSource(
    private val apiService: ApiService
) : BasePagingSource<MovieResponse, MoviesResponse>(
    fetchItems = { page ->
        apiService.getTopRatedMovies(page)
    }
)