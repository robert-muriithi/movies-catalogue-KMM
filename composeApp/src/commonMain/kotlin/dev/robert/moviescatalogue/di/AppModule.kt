package dev.robert.moviescatalogue.di

import dev.robert.moviescatalogue.data.ApiService
import dev.robert.moviescatalogue.data.MoviesApiServiceImpl
import dev.robert.moviescatalogue.data.database.getRoomDatabase
import dev.robert.moviescatalogue.domain.repository.MoviesRepository
import dev.robert.moviescatalogue.data.repository.MoviesRepositoryImpl
import dev.robert.moviescatalogue.domain.usecase.AddMovieToSaved
import dev.robert.moviescatalogue.domain.usecase.GetAiringToday
import dev.robert.moviescatalogue.domain.usecase.GetDiscoverMovies
import dev.robert.moviescatalogue.domain.usecase.GetDiscoverSeries
import dev.robert.moviescatalogue.domain.usecase.GetMovieCredits
import dev.robert.moviescatalogue.domain.usecase.GetMovieDetails
import dev.robert.moviescatalogue.domain.usecase.GetMovieReviews
import dev.robert.moviescatalogue.domain.usecase.GetPopularMovies
import dev.robert.moviescatalogue.domain.usecase.GetPopularSeries
import dev.robert.moviescatalogue.domain.usecase.GetSimilarMovies
import dev.robert.moviescatalogue.domain.usecase.GetTrendingUseCase
import dev.robert.moviescatalogue.domain.usecase.MovieDetails
import dev.robert.moviescatalogue.domain.usecase.NowPlayingMovies
import dev.robert.moviescatalogue.domain.usecase.RemoveFromSaved
import dev.robert.moviescatalogue.domain.usecase.TodayTrendingMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedTvSeries
import dev.robert.moviescatalogue.domain.usecase.TrendingTvSeries
import dev.robert.moviescatalogue.domain.usecase.TvSeriesDetails
import dev.robert.moviescatalogue.domain.usecase.UpcomingMovies
import dev.robert.moviescatalogue.presentation.home.HomeScreenViewModel
import dev.robert.moviescatalogue.presentation.movies.MoviesScreenViewModel
import dev.robert.moviescatalogue.presentation.movie_details.MovieDetailsViewModel
import dev.robert.moviescatalogue.presentation.saved.SavedMoviesViewModel
import dev.robert.moviescatalogue.presentation.tvshows.ShowsScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val targetModule: Module

val sharedModule = module {
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
    single { getRoomDatabase(get()) }
    // Api service
    singleOf(::MoviesApiServiceImpl) { bind<ApiService>() }

    // Repositories
    singleOf(::MoviesRepositoryImpl) { bind<MoviesRepository>() }

    // UseCases
    singleOf(::TodayTrendingMovies) { bind<TodayTrendingMovies>() }
    singleOf(::GetPopularMovies) { bind<GetPopularMovies>() }
    singleOf(::GetDiscoverMovies) { bind<GetDiscoverMovies>() }
    singleOf(::GetDiscoverSeries) { bind<GetDiscoverSeries>() }
    singleOf(::MovieDetails) { bind<MovieDetails>() }
    singleOf(::NowPlayingMovies) { bind<NowPlayingMovies>() }
    singleOf(::TopRatedMovies) { bind<TopRatedMovies>() }
    singleOf(::TopRatedTvSeries) { bind<TopRatedTvSeries>() }
    singleOf(::TrendingTvSeries) { bind<TrendingTvSeries>() }
    singleOf(::TvSeriesDetails) { bind<TvSeriesDetails>() }
    singleOf(::UpcomingMovies) { bind<UpcomingMovies>() }
    singleOf(::GetMovieDetails) { bind<GetMovieDetails>() }
    singleOf(::GetMovieReviews) { bind<GetMovieReviews>() }
    singleOf(::GetSimilarMovies) { bind<GetSimilarMovies>() }
    singleOf(::GetMovieCredits) { bind<GetMovieCredits>() }
    singleOf(::GetTrendingUseCase) { bind<GetTrendingUseCase>() }
    singleOf(::GetAiringToday) { bind<GetAiringToday>() }
    singleOf(::GetPopularSeries) { bind<GetPopularSeries>() }
    singleOf(::RemoveFromSaved) { bind<RemoveFromSaved>() }
    singleOf(::AddMovieToSaved) { bind<AddMovieToSaved>() }

    // ViewModels
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::MoviesScreenViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::SavedMoviesViewModel)
    viewModelOf(::ShowsScreenViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}
