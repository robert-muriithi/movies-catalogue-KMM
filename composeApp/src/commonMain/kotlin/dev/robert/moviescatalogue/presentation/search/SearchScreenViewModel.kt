package dev.robert.moviescatalogue.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import dev.robert.moviescatalogue.domain.usecase.MultiSearchUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class SearchScreenViewModel(
    private val multiSearchUseCase: MultiSearchUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults = _searchQuery
        .debounce(300)
        .filter { it.isNotEmpty() }
        .flatMapLatest { query ->
            search(query)
                .catch { throwable ->
                    println("SearchViewModel: Error searching: $throwable")
                    emit(PagingData.empty())
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty()
        )

    private fun search(query: String) = multiSearchUseCase(query)
        .catch { throwable ->
            println("SearchViewModel: Error searching: $throwable")
            emit(PagingData.empty())
        }

    fun onEvent(event: SearchScreenEvents) {
        when (event) {
            is SearchScreenEvents.Search -> _searchQuery.value = event.query
            is SearchScreenEvents.ClearSearch -> _searchQuery.value = ""
        }
    }
}

sealed class SearchScreenEvents {
    data class Search(val query: String) : SearchScreenEvents()
    data object ClearSearch : SearchScreenEvents()
}