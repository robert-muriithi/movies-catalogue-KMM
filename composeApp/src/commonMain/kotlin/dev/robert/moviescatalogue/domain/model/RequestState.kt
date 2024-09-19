package dev.robert.moviescatalogue.domain.model

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val message: String) : RequestState<Nothing>()

    fun isLoading() = this is Loading
    fun isIdle() = this is Idle
    fun isSuccess() = this is Success
    fun isError() = this is Error

    val successData get() = (this as? Success)?.data
    val error get() = (this as? Error)?.message
}