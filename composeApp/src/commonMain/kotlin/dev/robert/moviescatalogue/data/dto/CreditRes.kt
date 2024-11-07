package dev.robert.moviescatalogue.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
    @SerialName("cast")
    val castResponse: List<CastResponse>,
    @SerialName("crew")
    val crewResponse: List<CrewResponse>,
)

@Serializable
data class CastResponse(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("character")
    val character: String,
    @SerialName("credit_id")
    val creditId: String,
    @SerialName("gender")
    val gender: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("name")
    val name: String,
    @SerialName("order")
    val order: Int,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = "https://pixy.org/src/9/94083.png"
)

@Serializable
data class CrewResponse(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("credit_id")
    val creditId: String,
    @SerialName("department")
    val department: String,
    @SerialName("gender")
    val gender: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("job")
    val job: String,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    @SerialName("name")
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String? = "https://pixy.org/src/9/94083.png"
)