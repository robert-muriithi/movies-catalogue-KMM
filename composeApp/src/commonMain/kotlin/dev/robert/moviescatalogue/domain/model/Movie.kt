package dev.robert.moviescatalogue.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val mediaType: String = if (releaseDate.isEmpty()) "tv" else "movie",
    val firstAirDate: String,
    val name: String
)