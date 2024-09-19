package dev.robert.moviescatalogue.di

import dev.robert.moviescatalogue.data.api.ApiService
import dev.robert.moviescatalogue.data.api.MoviesApiServiceImpl
import dev.robert.moviescatalogue.data.api.repository.MoviesRepositoryImpl
import dev.robert.moviescatalogue.domain.repository.MoviesRepository
import dev.robert.moviescatalogue.domain.usecase.GetDiscoverMovies
import dev.robert.moviescatalogue.domain.usecase.GetDiscoverSeries
import dev.robert.moviescatalogue.domain.usecase.GetPopularMovies
import dev.robert.moviescatalogue.domain.usecase.MovieDetails
import dev.robert.moviescatalogue.domain.usecase.NowPlayingMovies
import dev.robert.moviescatalogue.domain.usecase.TodayTrendingMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedTvSeries
import dev.robert.moviescatalogue.domain.usecase.TrendingTvSeries
import dev.robert.moviescatalogue.domain.usecase.TvSeriesDetails
import dev.robert.moviescatalogue.domain.usecase.UpcomingMovies
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val appModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }
    single<ApiService> { MoviesApiServiceImpl(httpClient = get()) }
    single<MoviesRepository> { MoviesRepositoryImpl(moviesApi = get()) }
    single<TodayTrendingMovies> { TodayTrendingMovies(repository = get()) }
    single<GetPopularMovies> { GetPopularMovies(repository = get()) }
    single<GetDiscoverMovies> { GetDiscoverMovies(repository = get()) }
    single<GetDiscoverSeries> { GetDiscoverSeries(repository = get()) }
    single<MovieDetails> { MovieDetails(repository = get()) }
    single<NowPlayingMovies> { NowPlayingMovies(repository = get()) }
    single<TopRatedMovies> { TopRatedMovies(repository = get()) }
    single<TopRatedTvSeries> { TopRatedTvSeries(repository = get()) }
    single<TrendingTvSeries> { TrendingTvSeries(repository = get()) }
    single<TvSeriesDetails> { TvSeriesDetails(repository = get()) }
    single<UpcomingMovies> { UpcomingMovies(repository = get()) }
}

