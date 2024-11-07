package dev.robert.moviescatalogue.domain.model

import kotlinx.serialization.SerialName

data class SearchResult(
    val adult: Boolean?,
    val backdropPath: String?,
    val firstAirDate: String?,
    val genreIds: List<Int>?,
    val id: Int,
    @SerialName("media_type")
    val mediaType: String,
    @SerialName("name")
    val name: String?,
    @SerialName("origin_country")
    val originCountry: List<String>?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_name")
    val originalName: String?,
    @SerialName("original_title")
    val originalTitle: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("video")
    val video: Boolean?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
)
