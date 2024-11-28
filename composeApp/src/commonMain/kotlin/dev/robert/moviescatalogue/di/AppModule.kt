package dev.robert.moviescatalogue.di

import dev.robert.moviescatalogue.MainViewModel
import dev.robert.moviescatalogue.data.ApiService
import dev.robert.moviescatalogue.data.MoviesApiServiceImpl
import dev.robert.moviescatalogue.data.database.getRoomDatabase
import dev.robert.moviescatalogue.data.datastore.MoviesCataloguePrefs
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
import dev.robert.moviescatalogue.domain.usecase.MultiSearchUseCase
import dev.robert.moviescatalogue.domain.usecase.NowPlayingMovies
import dev.robert.moviescatalogue.domain.usecase.RemoveFromSaved
import dev.robert.moviescatalogue.domain.usecase.SavedStatus
import dev.robert.moviescatalogue.domain.usecase.TodayTrendingMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedMovies
import dev.robert.moviescatalogue.domain.usecase.TopRatedTvSeries
import dev.robert.moviescatalogue.domain.usecase.TrendingTvSeries
import dev.robert.moviescatalogue.domain.usecase.UpcomingMovies
import dev.robert.moviescatalogue.presentation.home.HomeScreenViewModel
import dev.robert.moviescatalogue.presentation.home.view_all.ViewAllViewModel
import dev.robert.moviescatalogue.presentation.movies.MoviesScreenViewModel
import dev.robert.moviescatalogue.presentation.movie_details.MovieDetailsViewModel
import dev.robert.moviescatalogue.presentation.saved.SavedMoviesViewModel
import dev.robert.moviescatalogue.presentation.search.SearchScreenViewModel
import dev.robert.moviescatalogue.presentation.settings.SettingsViewModel
import dev.robert.moviescatalogue.presentation.tvshows.ShowsScreenViewModel
import dev.robert.moviescatalogue.presentation.similar.SimilarScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val targetModule: Module

val sharedModule get() = module {
        includes(
            httpClientModule,
            databaseModule,
            apiServiceModule,
            repositoryModule,
            useCaseModules,
            viewModelModules,
            prefs
        )
}

val httpClientModule = module {
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
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }
}

val databaseModule = module {
    single { getRoomDatabase(get()) }
}

val apiServiceModule = module {
    singleOf(::MoviesApiServiceImpl) { bind<ApiService>() }
}
val repositoryModule = module {
    singleOf(::MoviesRepositoryImpl) { bind<MoviesRepository>() }
}

val useCaseModules = module {
    singleOf(::TodayTrendingMovies) { bind<TodayTrendingMovies>() }
    singleOf(::GetPopularMovies) { bind<GetPopularMovies>() }
    singleOf(::GetDiscoverMovies) { bind<GetDiscoverMovies>() }
    singleOf(::GetDiscoverSeries) { bind<GetDiscoverSeries>() }
    singleOf(::MovieDetails) { bind<MovieDetails>() }
    singleOf(::NowPlayingMovies) { bind<NowPlayingMovies>() }
    singleOf(::TopRatedMovies) { bind<TopRatedMovies>() }
    singleOf(::TopRatedTvSeries) { bind<TopRatedTvSeries>() }
    singleOf(::TrendingTvSeries) { bind<TrendingTvSeries>() }
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
    singleOf(::MultiSearchUseCase) { bind<MultiSearchUseCase>() }
    singleOf(::SavedStatus) { bind<SavedStatus>() }
}

val viewModelModules = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::MoviesScreenViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::SavedMoviesViewModel)
    viewModelOf(::ShowsScreenViewModel)
    viewModelOf(::ViewAllViewModel)
    viewModelOf(::SearchScreenViewModel)
    viewModelOf(::SimilarScreenViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::MainViewModel)
}

//val dataStoreModule = module {
//    single {
//        createDataStore(producePath = { dataStoreFileName })
//    }
//}

val prefs = module {
    single { MoviesCataloguePrefs(get()) }
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}
