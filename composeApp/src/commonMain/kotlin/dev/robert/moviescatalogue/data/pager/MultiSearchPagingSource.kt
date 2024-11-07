package dev.robert.moviescatalogue.data.pager

import dev.robert.moviescatalogue.data.ApiService
import dev.robert.moviescatalogue.data.dto.MultiSearchResponse
import dev.robert.moviescatalogue.data.dto.SearchResult

class MultiSearchPagingSource(
    private val api : ApiService,
    private val query: String
) : BasePagingSource<SearchResult, MultiSearchResponse>(
    fetchItems = { page ->
        api.multiSearch(page = page, query = query)
    }
)