package dev.robert.moviescatalogue.presentation.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.model.MovieCast
import dev.robert.moviescatalogue.domain.model.MovieDetails
import dev.robert.moviescatalogue.domain.model.MovieReview
import dev.robert.moviescatalogue.domain.usecase.AddMovieToSaved
import dev.robert.moviescatalogue.domain.usecase.GetMovieCredits
import dev.robert.moviescatalogue.domain.usecase.GetMovieDetails
import dev.robert.moviescatalogue.domain.usecase.GetMovieReviews
import dev.robert.moviescatalogue.domain.usecase.GetSimilarMovies
import dev.robert.moviescatalogue.domain.usecase.RemoveFromSaved
import dev.robert.moviescatalogue.domain.usecase.SavedStatus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val getMovieDetails: GetMovieDetails,
    private val getMovieReviews: GetMovieReviews,
    private val getSimilarMovies: GetSimilarMovies,
    private val getMovieCredits: GetMovieCredits,
    private val addMovieToSaved: AddMovieToSaved,
    private val removeMovieFromSaved: RemoveFromSaved,
    private val savedStatus: SavedStatus
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
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )




    private fun movieDetails(movieId: Int, mediaType: String) {
        val isMovie = mediaType == "movie"
        _movieDetail.update { it.copy(isLoading = true) }
        viewModelScope.launch(handler) {
            val movieDetailsFlow = getMovieDetails(movieId = movieId, isMovie = isMovie)
            val creditsFlow = getMovieCredits(movieId = movieId, isMovie = isMovie)
            val savedFlow = savedStatus(movieId = movieId)
            combine(
                movieDetailsFlow,
                creditsFlow,
                savedFlow
            ) { movieDetails, credits, saved ->
                MovieDetailState(
                    movieDetails = movieDetails,
                    credits = credits,
                    isLoading = false,
                    isSaved = saved
                )
            }.collectLatest { state ->
                _movieDetail.update {
                    it.copy(
                        movieDetails = state.movieDetails,
                        credits = state.credits,
                        isLoading = state.isLoading,
                        error = state.error,
                        isSaved = state.isSaved
                    )
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

    private fun fetchSimilar(movieId: Int, mediaType: String) {
        val isMovie = mediaType == "movie"
        viewModelScope.launch {
            getSimilarMovies(movieId = movieId, isMovie = isMovie).collectLatest { similarMovies ->
                _similarMovies.update { similarMovies }
            }
        }
    }

    private fun getReviews(movieId: Int, mediaType: String) {
        val isMovie = mediaType == "movie"
        viewModelScope.launch {
            getMovieReviews(movieId = movieId, isMovie = isMovie).collectLatest { movieReviews ->
                _movieReviews.update { movieReviews }
            }
        }
    }


    fun onEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.GetMovieDetails -> movieDetails(event.movieId, event.mediaType)
            is MovieDetailEvent.GetSimilarMovies -> fetchSimilar(event.movieId, event.mediaType)
            is MovieDetailEvent.AddToSaved -> addToSaved(event.movie)
            is MovieDetailEvent.RemoveFromSaved -> removeFromSaved(event.movieId)
            is MovieDetailEvent.GetMovieReviews -> getReviews(event.movieId, event.mediaType)
        }
    }

}

data class MovieDetailState(
    val movieDetails: MovieDetails = MovieDetails(),
    val credits: List<MovieCast> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isSaved: Boolean = false
)

sealed class MovieDetailEvent {
    data class GetMovieDetails(val movieId: Int, val mediaType: String) : MovieDetailEvent()
    data class GetSimilarMovies(val movieId: Int, val mediaType: String) : MovieDetailEvent()
    data class GetMovieReviews(val movieId: Int, val mediaType: String) : MovieDetailEvent()
    data class AddToSaved(val movie: Movie) : MovieDetailEvent()
    data class RemoveFromSaved(val movieId: Int) : MovieDetailEvent()
}