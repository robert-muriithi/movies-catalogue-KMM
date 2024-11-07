package dev.robert.moviescatalogue.data

import dev.robert.moviescatalogue.data.constants.Constants.INITIAL_PAGE
import dev.robert.moviescatalogue.data.dto.CreditsResponse
import dev.robert.moviescatalogue.data.dto.GenresResponse
import dev.robert.moviescatalogue.data.dto.MovieDetailsResponse
import dev.robert.moviescatalogue.data.dto.MoviesResponse
import dev.robert.moviescatalogue.data.dto.MultiSearchResponse
import dev.robert.moviescatalogue.data.dto.ReviewResponse

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
    suspend fun getMovieDetails(movieId: Int, isMovie: Boolean) : MovieDetailsResponse
    suspend fun getMovieCredits(isMovie: Boolean, movieId: Int) : CreditsResponse
    suspend fun getMovieGenres() : GenresResponse
    suspend fun getTvSeriesGenres() : GenresResponse
    suspend fun multiSearch(query: String, page: Int = INITIAL_PAGE) : MultiSearchResponse
    suspend fun getMovieReviews(isMovie: Boolean, movieId: Int, page: Int = INITIAL_PAGE) : ReviewResponse
    suspend fun getSimilarMovies(isMovie: Boolean, movieId: Int, page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getAllTrending(page: Int = INITIAL_PAGE, window: String = "day") : MoviesResponse
    suspend fun getPopularTvShows(page: Int = INITIAL_PAGE) : MoviesResponse
    suspend fun getAirTodayTvShows(page: Int = INITIAL_PAGE) : MoviesResponse
}