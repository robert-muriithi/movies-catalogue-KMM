package dev.robert.moviescatalogue.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewAuthorDetails(
    @SerialName("avatar_path")
    val avatarPath: String? = null,
    @SerialName("name")
    val name: String,
    @SerialName("rating")
    val rating: Int,
    @SerialName("username")
    val username: String
)