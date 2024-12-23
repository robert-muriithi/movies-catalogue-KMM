package dev.robert.moviescatalogue.domain.model



data class MovieDetails(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val budget: Int? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    val imdbId: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
)

