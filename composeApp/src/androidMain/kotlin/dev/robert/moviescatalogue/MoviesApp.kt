package dev.robert.moviescatalogue

import android.app.Application
import dev.robert.moviescatalogue.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@MoviesApp)
        }
    }
}