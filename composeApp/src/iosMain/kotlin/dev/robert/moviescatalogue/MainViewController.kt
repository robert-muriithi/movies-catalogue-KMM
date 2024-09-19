package dev.robert.moviescatalogue

import androidx.compose.ui.window.ComposeUIViewController
import dev.robert.moviescatalogue.di.KoinInitializer

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().initialize()
    }
) {
    App()
}