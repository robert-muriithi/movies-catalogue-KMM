package dev.robert.moviescatalogue.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvSeriesDetails(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("genres")
    val genreDtos: List<GenreDto>,
    @SerialName("homepage")
    val homepage: String,
    @SerialName("id")
    val id: Int,
    @SerialName("in_production")
    val inProduction: Boolean,
    @SerialName("languages")
    val languages: List<String>,
    @SerialName("last_air_date")
    val lastAirDate: String,
    @SerialName("name")
    val name: String,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: String? = null,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("status")
    val status: String,
    @SerialName("tagline")
    val tagline: String,
    @SerialName("type")
    val type: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)