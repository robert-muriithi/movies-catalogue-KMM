package dev.robert.moviescatalogue.presentation.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.model.MovieCast
import dev.robert.moviescatalogue.domain.model.MovieDetails
import dev.robert.moviescatalogue.domain.model.MovieReview
import dev.robert.moviescatalogue.domain.repository.MoviesRepository
import dev.robert.moviescatalogue.domain.usecase.AddMovieToSaved
import dev.robert.moviescatalogue.domain.usecase.GetMovieCredits
import dev.robert.moviescatalogue.domain.usecase.GetMovieDetails
import dev.robert.moviescatalogue.domain.usecase.GetMovieReviews
import dev.robert.moviescatalogue.domain.usecase.GetSimilarMovies
import dev.robert.moviescatalogue.domain.usecase.RemoveFromSaved
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieDetails: GetMovieDetails,
    private val getMovieReviews: GetMovieReviews,
    private val getSimilarMovies: GetSimilarMovies,
    private val getMovieCredits: GetMovieCredits,
    private val addMovieToSaved: AddMovieToSaved,
    private val removeMovieFromSaved: RemoveFromSaved
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        _movieDetail.update {
            it.copy(
                isLoading = false,
                error = exception.message ?: "An error occurred"
            )
        }
    }

    private val _movieDetail: MutableStateFlow<MovieDetailState> =
        MutableStateFlow(MovieDetailState())
    val movieDetail = _movieDetail.asStateFlow()

    private val _movieReviews: MutableStateFlow<PagingData<MovieReview>> =
        MutableStateFlow(PagingData.empty())

    val movieReviews: StateFlow<PagingData<MovieReview>> = _movieReviews
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )


    private val _similarMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())

    val similarMovies: StateFlow<PagingData<Movie>> = _similarMovies
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )




    private fun movieDetails(movieId: Int) {
        _movieDetail.update { it.copy(isLoading = true) }
        viewModelScope.launch(handler) {
            getMovieDetails(movieId).collectLatest { movieDetails ->
                getMovieCredits(movieId).collectLatest { movieCredits ->
                    _movieDetail.update {
                        it.copy(
                            movieDetails = movieDetails,
                            credits = movieCredits,
                            isLoading = false
                        )
                    }
                }
//            getMovieReviews(movieId).collectLatest { movieReviews ->
//                _movieReviews.value = movieReviews
//            }
                getSimilarMovies(movieId).collectLatest { similarMovies ->
                    _similarMovies.value = similarMovies
                }
            }
        }
    }

    private fun addToSaved(movie: Movie) {
        viewModelScope.launch {
            addMovieToSaved(movie)
            _movieDetail.update { it.copy(isSaved = true) }
        }
    }

    private fun removeFromSaved(movieId: Int) {
        viewModelScope.launch {
            removeMovieFromSaved(movieId)
            _movieDetail.update { it.copy(isSaved = false) }
        }
    }

    fun onEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.GetMovieDetails -> movieDetails(event.movieId)
            is MovieDetailEvent.AddToSaved -> addToSaved(event.movie)
            is MovieDetailEvent.RemoveFromSaved -> removeFromSaved(event.movieId)
        }
    }

}

data class MovieDetailState(
    val movieDetails: MovieDetails = MovieDetails(),
    val credits : List<MovieCast> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isSaved: Boolean = false
)

sealed class MovieDetailEvent {
    data class GetMovieDetails(val movieId: Int) : MovieDetailEvent()
    data class AddToSaved(val movie: Movie) : MovieDetailEvent()
    data class RemoveFromSaved(val movieId: Int) : MovieDetailEvent()
}