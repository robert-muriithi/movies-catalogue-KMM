package dev.robert.moviescatalogue.di

import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun initialize() {
        startKoin {
            modules(listOf(
                appModule,
                viewModelModule
            ))
        }
    }
}