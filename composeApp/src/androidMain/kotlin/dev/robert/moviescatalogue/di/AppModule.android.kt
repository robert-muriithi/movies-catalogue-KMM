package dev.robert.moviescatalogue.di

import dev.robert.moviescatalogue.data.database.getDatabaseBuilder
import dev.robert.moviescatalogue.data.datastore.createDataStore
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder(context = get()) }
    single { createDataStore(context = get()) }
}