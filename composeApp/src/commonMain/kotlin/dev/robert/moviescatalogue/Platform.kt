package dev.robert.moviescatalogue

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform