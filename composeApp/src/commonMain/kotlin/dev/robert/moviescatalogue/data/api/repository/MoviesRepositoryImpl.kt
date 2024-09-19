package dev.robert.moviescatalogue.data.api.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.map
import dev.robert.moviescatalogue.data.api.ApiService
import dev.robert.moviescatalogue.data.api.constants.Constants.PAGING_SIZE
import dev.robert.moviescatalogue.data.api.dto.MovieResponse
import dev.robert.moviescatalogue.data.api.pager.DiscoverMoviesPagingSource
import dev.robert.moviescatalogue.data.api.pager.DiscoverTvSeriesPagingSource
import dev.robert.moviescatalogue.data.api.pager.NowPlayingMoviesPagingSource
import dev.robert.moviescatalogue.data.api.pager.PopularMoviesPagingSource
import dev.robert.moviescatalogue.data.api.pager.TopRatedMoviesPagingSource
import dev.robert.moviescatalogue.data.api.pager.TopRatedTvSeriesPagingSource
import dev.robert.moviescatalogue.data.api.pager.TrendingTodayMoviesPagingSource
import dev.robert.moviescatalogue.data.api.pager.TrendingTvSeriesPagingSource
import dev.robert.moviescatalogue.data.api.pager.UpcomingMoviesPagingSource
import dev.robert.moviescatalogue.data.api.toMovie
import dev.robert.moviescatalogue.domain.repository.MoviesRepository
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val moviesApi: ApiService
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
                movieResponse.toMovie().copy(type = "tv")
            }
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails> {
        TODO("Not yet implemented")
    }

    override fun getTvSeriesDetails(tvSeriesId: Int): Flow<MovieDetails> {
        TODO("Not yet implemented")
    }
}