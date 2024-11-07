package dev.robert.moviescatalogue.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("page")
    override val page: Int,
    @SerialName("results")
    override val results: List<MovieReviewResponse>,
    @SerialName("total_pages")
    override val totalPages: Int,
    @SerialName("total_results")
    override val totalResults: Int
) : PaginatedResponse<MovieReviewResponse>




@Serializable
data class MovieReviewResponse(
    @SerialName("author")
    val author: String,
    @SerialName("content")
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("url")
    val url: String
)