package dev.robert.moviescatalogue.domain.repository

import app.cash.paging.PagingData
import dev.robert.moviescatalogue.data.api.dto.MovieResponse
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getDiscoverMovies(): Flow<PagingData<Movie>>
    fun getDiscoverTvSeries(): Flow<PagingData<Movie>>
    fun getTodayTrendingMovies(): Flow<PagingData<Movie>>
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getUpcomingMovies(): Flow<PagingData<Movie>>
    fun getTopRatedMovies(): Flow<PagingData<Movie>>
    fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    fun getTrendingTvSeries(): Flow<PagingData<Movie>>
    fun getTopRatedTvSeries(): Flow<PagingData<Movie>>
    fun getMovieDetails(movieId: Int): Flow<MovieDetails>
    fun getTvSeriesDetails(tvSeriesId: Int): Flow<MovieDetails>
//    fun searchMovies(query: String): Flow<PagingData<MovieResponse>>
//    fun getMovieCredits(movieId: Int): Flow<PagingData<MovieResponse>>
}