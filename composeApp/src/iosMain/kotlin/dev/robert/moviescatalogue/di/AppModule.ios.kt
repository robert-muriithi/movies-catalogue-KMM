package dev.robert.moviescatalogue.di

import dev.robert.moviescatalogue.data.database.getDatabaseBuilder
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder() }
}