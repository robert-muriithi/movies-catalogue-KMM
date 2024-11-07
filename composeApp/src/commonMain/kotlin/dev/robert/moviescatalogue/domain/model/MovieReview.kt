package dev.robert.moviescatalogue.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieReview(
    val author: String,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)
