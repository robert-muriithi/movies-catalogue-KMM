package dev.robert.moviescatalogue.data.pager

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import dev.robert.moviescatalogue.data.dto.PaginatedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

abstract class BasePagingSource<T : Any, R : PaginatedResponse<T>>(
    private val fetchItems: suspend (page: Int) -> R
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            val response = withContext(Dispatchers.IO) {
                fetchItems(page)
            }
            val data = response.results
            val nextPage = (page + 1).takeIf { response.page < response.totalPages }
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: 1
        }
    }
}