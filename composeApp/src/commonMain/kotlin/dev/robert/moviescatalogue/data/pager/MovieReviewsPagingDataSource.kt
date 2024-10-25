package dev.robert.moviescatalogue.data.pager

import dev.robert.moviescatalogue.data.ApiService
import dev.robert.moviescatalogue.data.dto.MovieReviewResponse
import dev.robert.moviescatalogue.data.dto.ReviewResponse

class MovieReviewsPagingDataSource(
    private val api : ApiService
) : BasePagingSource<MovieReviewResponse, ReviewResponse>(
    fetchItems = { page ->
        api.getMovieReviews(page)
    }
)