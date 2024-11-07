package dev.robert.moviescatalogue.data.pager

import dev.robert.moviescatalogue.data.ApiService
import dev.robert.moviescatalogue.data.dto.MovieResponse
import dev.robert.moviescatalogue.data.dto.MoviesResponse

class SimilarMoviesPagingSource(
    private val apiService: ApiService,
    private val movieId: Int,
    private val isMovie: Boolean
) : BasePagingSource<MovieResponse, MoviesResponse>(
    fetchItems = { page -> apiService.getSimilarMovies(page = page, movieId = movieId, isMovie = isMovie) }
)