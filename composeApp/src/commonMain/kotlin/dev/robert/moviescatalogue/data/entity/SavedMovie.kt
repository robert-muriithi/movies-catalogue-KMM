package dev.robert.moviescatalogue.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved")
data class SavedMovie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val mediaType: String? = null,
    val firstAirDate: String? = null,
    val name: String? = null
)