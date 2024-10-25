package dev.robert.moviescatalogue

import androidx.compose.ui.window.ComposeUIViewController
import dev.robert.moviescatalogue.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }