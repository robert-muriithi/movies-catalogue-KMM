package dev.robert.moviescatalogue.domain.model

import dev.robert.moviescatalogue.data.dto.ReviewAuthorDetails

data class MovieReview(
    val author: String,
    val reviewAuthorDetails: ReviewAuthorDetails,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)
