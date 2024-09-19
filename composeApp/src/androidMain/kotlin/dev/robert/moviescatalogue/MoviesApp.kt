package dev.robert.moviescatalogue

import android.app.Application
import dev.robert.moviescatalogue.di.KoinInitializer

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).initialize()
    }
}