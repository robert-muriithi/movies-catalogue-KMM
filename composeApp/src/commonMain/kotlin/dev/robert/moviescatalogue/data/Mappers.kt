package dev.robert.moviescatalogue.data

import dev.robert.moviescatalogue.data.dto.CastResponse
import dev.robert.moviescatalogue.data.dto.GenreDto
import dev.robert.moviescatalogue.data.dto.MovieDetailsResponse
import dev.robert.moviescatalogue.data.dto.MovieResponse
import dev.robert.moviescatalogue.data.dto.MovieResult
import dev.robert.moviescatalogue.data.dto.MovieReviewResponse
import dev.robert.moviescatalogue.data.dto.TvShowResult
import dev.robert.moviescatalogue.data.entity.SavedMovie
import dev.robert.moviescatalogue.domain.model.Genre
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.model.MovieCast
import dev.robert.moviescatalogue.domain.model.MovieDetails
import dev.robert.moviescatalogue.domain.model.MovieReview


fun MovieResponse.toMovie() = Movie(
    adult = adult ?: false,
    backdropPath = backdropPath ?: "",
    genreIds = genreIds ?: emptyList(),
    id = id ?: 0,
    originalLanguage = originalLanguage ?: "",
    originalTitle = originalTitle ?: "",
    overview = overview ?: "",
    popularity = popularity ?: 0.0,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
//    mediaType = mediaType ?: "",
    firstAirDate = firstAirDate ?: "",
    name = name ?: ""
)

fun MovieDetailsResponse.toMovieDetails() = MovieDetails(
    adult = adult ?: false,
    backdropPath = backdropPath ?: "",
    genres = genreDtos?.toGenres(),
    id = id ?: 0,
    originalLanguage = originalLanguage ?: "",
    originalTitle = originalTitle ?: "",
    overview = overview ?: "",
    popularity = popularity ?: 0.0,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate ?: "",
    title = title ?: "",
    video = video ?: false,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0
)

fun GenreDto.toGenre() = Genre(
    id = id,
    name = name
)

fun List<GenreDto>.toGenres() = map { it.toGenre() }

fun MovieReviewResponse.toReview() = MovieReview(
    author = author,
    content = content,
    createdAt = createdAt,
    id = id,
    updatedAt = updatedAt,
    url = url
)

fun Movie.toMovieEntity() = SavedMovie(
    backdropPath = backdropPath,
    id = id,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    mediaType = mediaType,
    firstAirDate = firstAirDate,
    name = name
)

fun SavedMovie.toMovie() = Movie(
    adult = false,
    backdropPath = backdropPath,
    genreIds = emptyList(),
    id = id,
    originalLanguage = "",
    originalTitle = "",
    overview = overview,
    popularity = 0.0,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = false,
    voteAverage = voteAverage,
    voteCount = voteCount,
//    mediaType = mediaType ?: "",
    firstAirDate = firstAirDate ?: "",
    name = name ?: ""
)

fun CastResponse.toMovieCast() = MovieCast(
    adult = adult,
    character = character,
    creditId = creditId,
    gender = gender,
    id = id,
    knownForDepartment = knownForDepartment,
    name = name,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath ?: ""
)

fun List<CastResponse>.toMovieCasts() = map { it.toMovieCast() }

fun MovieResult.toMovie() = Movie(
    adult = adult,
    backdropPath = backdropPath ?: "",
    genreIds = genreIds,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
//    mediaType = mediaType,
    firstAirDate = "",
    name = ""
)

fun TvShowResult.toMovie() = Movie(
    adult = adult,
    backdropPath = backdropPath ?: "",
    genreIds = genreIds,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = "",
    overview = overview,
    popularity = popularity,
    posterPath = posterPath ?: "",
    releaseDate = "",
    title = "",
    video = false,
    voteAverage = voteAverage,
    voteCount = voteCount,
//    mediaType = mediaType,
    firstAirDate = firstAirDate,
    name = name
)