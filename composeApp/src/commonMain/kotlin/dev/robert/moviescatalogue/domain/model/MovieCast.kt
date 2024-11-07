package dev.robert.moviescatalogue.domain.model


data class MovieCast (
    val adult: Boolean,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    val originalName: String,
    val popularity: Double,
    val profilePath: String = "https://pixy.org/src/30/302909.png"
)