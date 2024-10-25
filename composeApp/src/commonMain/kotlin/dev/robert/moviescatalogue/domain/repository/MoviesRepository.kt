package dev.robert.moviescatalogue.domain.repository

import app.cash.paging.PagingData
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.model.MovieCast
import dev.robert.moviescatalogue.domain.model.MovieDetails
import dev.robert.moviescatalogue.domain.model.MovieReview
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getDiscoverMovies(): Flow<PagingData<Movie>>
    fun getDiscoverTvSeries(): Flow<PagingData<Movie>>
    fun getTodayTrendingMovies(): Flow<PagingData<Movie>>
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getPopularTvSeries() : Flow<PagingData<Movie>>
    fun getUpcomingMovies(): Flow<PagingData<Movie>>
    fun getTopRatedMovies(): Flow<PagingData<Movie>>
    fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    fun getTrendingTvSeries(): Flow<PagingData<Movie>>
    fun getTopRatedTvSeries(): Flow<PagingData<Movie>>
    fun getMovieDetails(movieId: Int): Flow<MovieDetails>
    fun getTvSeriesDetails(tvSeriesId: Int): Flow<MovieDetails>
    fun getMovieReviews(movieId: Int): Flow<PagingData<MovieReview>>
    fun getMovieCredits(movieId: Int): Flow<List<MovieCast>>
    fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>>
    fun getTrending() : Flow<PagingData<Movie>>
    fun getAirTodayTvShows() : Flow<PagingData<Movie>>
    suspend fun addMovieToWatchlist(movie: Movie)
    suspend fun removeMovieFromWatchlist(movieId: Int)
    fun getSavedMovies(): Flow<List<Movie>>
//    fun searchMovies(query: String): Flow<PagingData<MovieResponse>>
//    fun getMovieCredits(movieId: Int): Flow<PagingData<MovieResponse>>
}