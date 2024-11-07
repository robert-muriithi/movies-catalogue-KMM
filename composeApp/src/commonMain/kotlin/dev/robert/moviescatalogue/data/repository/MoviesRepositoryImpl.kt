package dev.robert.moviescatalogue.data.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.map
import dev.robert.moviescatalogue.data.ApiService
import dev.robert.moviescatalogue.data.constants.Constants.PAGING_SIZE
import dev.robert.moviescatalogue.data.database.SavedMoviesDatabase
import dev.robert.moviescatalogue.data.dto.SearchResult
import dev.robert.moviescatalogue.data.pager.AiringTodayPagingSource
import dev.robert.moviescatalogue.data.pager.DiscoverMoviesPagingSource
import dev.robert.moviescatalogue.data.pager.DiscoverTvSeriesPagingSource
import dev.robert.moviescatalogue.data.pager.MovieReviewsPagingDataSource
import dev.robert.moviescatalogue.data.pager.MultiSearchPagingSource
import dev.robert.moviescatalogue.data.pager.NowPlayingMoviesPagingSource
import dev.robert.moviescatalogue.data.pager.PopularMoviesPagingSource
import dev.robert.moviescatalogue.data.pager.PopularTvSeriesPagingSource
import dev.robert.moviescatalogue.data.pager.SimilarMoviesPagingSource
import dev.robert.moviescatalogue.data.pager.TopRatedMoviesPagingSource
import dev.robert.moviescatalogue.data.pager.TopRatedTvSeriesPagingSource
import dev.robert.moviescatalogue.data.pager.TrendingPagingSource
import dev.robert.moviescatalogue.data.pager.TrendingTodayMoviesPagingSource
import dev.robert.moviescatalogue.data.pager.TrendingTvSeriesPagingSource
import dev.robert.moviescatalogue.data.pager.UpcomingMoviesPagingSource
import dev.robert.moviescatalogue.data.toMovie
import dev.robert.moviescatalogue.data.toMovieCasts
import dev.robert.moviescatalogue.data.toMovieDetails
import dev.robert.moviescatalogue.data.toMovieEntity
import dev.robert.moviescatalogue.data.toReview
import dev.robert.moviescatalogue.domain.repository.MoviesRepository
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.model.MovieCast
import dev.robert.moviescatalogue.domain.model.MovieDetails
import dev.robert.moviescatalogue.domain.model.MovieReview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val moviesApi: ApiService,
    private val database: SavedMoviesDatabase
): MoviesRepository {
    override fun getTodayTrendingMovies(): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TrendingTodayMoviesPagingSource(moviesApi)
            }
        ) .flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PopularMoviesPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }

    override fun getPopularTvSeries(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PopularTvSeriesPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }

    override fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UpcomingMoviesPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TopRatedMoviesPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }



    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NowPlayingMoviesPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }


    override fun getTrendingTvSeries(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TrendingTvSeriesPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }


    override fun getTopRatedTvSeries(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TopRatedTvSeriesPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }

    override fun getDiscoverMovies(): Flow<PagingData<Movie>> {
       return Pager(
           config = PagingConfig(
               pageSize = PAGING_SIZE,
               enablePlaceholders = false
           ),
           pagingSourceFactory = {
               DiscoverMoviesPagingSource(moviesApi)
           }
       ).flow.map { pagingData ->
           pagingData.map { movieResponse ->
               movieResponse.toMovie()
           }
       }
    }

    override fun getDiscoverTvSeries(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                DiscoverTvSeriesPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }

    override fun getMovieDetails(movieId: Int, isMovie: Boolean): Flow<MovieDetails> {
        return flow {
            emit(moviesApi.getMovieDetails(movieId, isMovie).toMovieDetails())
        }
    }


    override fun getMovieReviews(isMovie: Boolean, movieId: Int): Flow<PagingData<MovieReview>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieReviewsPagingDataSource(api = moviesApi, movieId = movieId, isMovie = isMovie)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieReviewResponse ->
                movieReviewResponse.toReview()
            }
        }
    }

    override fun getMovieCredits(isMovie: Boolean, movieId: Int): Flow<List<MovieCast>> {
        return flow{
            emit(moviesApi.getMovieCredits(movieId = movieId, isMovie = isMovie).castResponse.toMovieCasts())
        }
    }

    override fun getSimilarMovies(movieId: Int, isMovie: Boolean): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SimilarMoviesPagingSource(apiService = moviesApi, movieId = movieId, isMovie = isMovie)
            }
        ).flow.map { pagingData ->
            pagingData.map { movieResponse ->
                movieResponse.toMovie()
            }
        }
    }

    override fun getTrending(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TrendingPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { response ->
                response.toMovie()
            }
        }
    }

    override fun getAirTodayTvShows(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AiringTodayPagingSource(moviesApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { response ->
                response.toMovie()
            }
        }
    }

    override suspend fun addMovieToWatchlist(movie: Movie) {
        database.savedMovieDao().insert(movie.toMovieEntity())
    }

    override suspend fun removeMovieFromWatchlist(movieId: Int) {
        database.savedMovieDao().delete(movieId)
    }

    override fun getSavedMovies(): Flow<List<Movie>> {
        return database.savedMovieDao().getAll().map { movies ->
            movies.map { it.toMovie() }
        }
    }


    override fun multiSearch(query: String): Flow<PagingData<SearchResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MultiSearchPagingSource(moviesApi, query)
            }
        ).flow
    }

    override fun isSaved(movieId: Int): Flow<Boolean> {
        return database.savedMovieDao().isSaved(movieId)
    }
}