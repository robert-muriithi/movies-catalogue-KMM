package dev.robert.moviescatalogue.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerialName("genres")
    val genres: List<GenreDto>
) {
    @Serializable
    data class GenreDto(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String
    )
}