package dev.robert.moviescatalogue.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultiSearchResponse(
    @SerialName("page")
    override val page: Int,
    @SerialName("results")
    override val results: List<SearchResult>,
    @SerialName("total_pages")
    override val totalPages: Int,
    @SerialName("total_results")
    override val totalResults: Int
) : PaginatedResponse<SearchResult>

@Serializable
sealed class SearchResult {
    abstract val id: Int
    abstract val mediaType: String
    abstract val adult: Boolean
    abstract val popularity: Double
}

@Serializable
@SerialName("movie")
data class MovieResult(
    override val id: Int,
    @SerialName("media_type")
    override val mediaType: String,
    override val adult: Boolean,
    override val popularity: Double,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("release_date")
    val releaseDate: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
) : SearchResult()

@Serializable
@SerialName("tv")
data class TvShowResult(
    override val id: Int,
    @SerialName("media_type")
    override val mediaType: String,
    override val adult: Boolean,
    override val popularity: Double,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val overview: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("origin_country")
    val originCountry: List<String>,
) : SearchResult()

@Serializable
@SerialName("person")
data class PersonResult(
    override val id: Int,
    @SerialName("media_type")
    override val mediaType: String,
    override val adult: Boolean,
    override val popularity: Double,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val gender: Int,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("profile_path")
    val profilePath: String?,
    @SerialName("known_for")
    val knownFor: List<KnownForMovie>,
) : SearchResult()

@Serializable
data class KnownForMovie(
    val id: Int,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("media_type")
    val mediaType: String,
    val adult: Boolean,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val popularity: Double,
    @SerialName("release_date")
    val releaseDate: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)