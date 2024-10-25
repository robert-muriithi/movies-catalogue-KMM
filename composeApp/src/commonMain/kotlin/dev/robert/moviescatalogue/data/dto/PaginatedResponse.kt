package dev.robert.moviescatalogue.data.dto


interface PaginatedResponse<T> {
    val page: Int
    val results: List<T>
    val totalPages: Int
    val totalResults: Int
}