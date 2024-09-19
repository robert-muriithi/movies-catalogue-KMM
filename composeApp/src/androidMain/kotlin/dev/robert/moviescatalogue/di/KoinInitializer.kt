package dev.robert.moviescatalogue.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val context: Context
) {
    actual fun initialize() {
        startKoin {
            androidContext(context)
            androidLogger()
            modules(listOf(
                appModule,
                viewModelModule
            ))
        }
    }
}