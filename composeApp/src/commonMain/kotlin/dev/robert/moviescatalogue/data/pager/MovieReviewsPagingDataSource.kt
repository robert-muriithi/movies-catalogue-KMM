package dev.robert.moviescatalogue.data.pager

import dev.robert.moviescatalogue.data.ApiService
import dev.robert.moviescatalogue.data.dto.MovieReviewResponse
import dev.robert.moviescatalogue.data.dto.ReviewResponse

class MovieReviewsPagingDataSource(
    private val api : ApiService,
    private val movieId: Int,
    private val isMovie: Boolean
) : BasePagingSource<MovieReviewResponse, ReviewResponse>(
    fetchItems = { page ->
        api.getMovieReviews(page = page, movieId = movieId, isMovie = isMovie)
    }
)