package dev.robert.moviescatalogue.data.api

import dev.robert.moviescatalogue.data.api.constants.Constants.INITIAL_PAGE
import dev.robert.moviescatalogue.data.api.dto.CreditsResponse
import dev.robert.moviescatalogue.data.api.dto.GenresResponse
import dev.robert.moviescatalogue.data.api.dto.MovieDetailsResponse
import dev.robert.moviescatalogue.data.api.dto.MoviesResponse
import dev.robert.moviescatalogue.data.api.dto.MultiSearchResponse

interface ApiService {
    suspend fun getDiscoverMovies(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getDiscoverTvSeries(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getTrendingTodayMovies(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getPopularMovies(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getUpcomingMovies(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getTopRatedMovies(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getNowPlayingMovies(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getTrendingTvSeries(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getTopRatedTvSeries(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getMovieDetails(movieId: Int) : MovieDetailsResponse
    suspend fun getTvSeriesDetails(tvSeriesId: Int) : MovieDetailsResponse
    suspend fun getTvSeriesCredits(tvSeriesId: Int) : CreditsResponse
    suspend fun getMovieCredits(movieId: Int) : CreditsResponse
    suspend fun getMovieGenres() : GenresResponse
    suspend fun getTvSeriesGenres() : GenresResponse
    suspend fun searchMovies(query: String, page: Int = INITIAL_PAGE) : MultiSearchResponse
}