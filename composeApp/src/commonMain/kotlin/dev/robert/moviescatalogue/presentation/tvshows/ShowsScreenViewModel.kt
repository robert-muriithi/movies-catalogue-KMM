package dev.robert.moviescatalogue.presentation.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import dev.robert.moviescatalogue.domain.usecase.GetAiringToday
import dev.robert.moviescatalogue.domain.usecase.GetPopularSeries
import dev.robert.moviescatalogue.domain.usecase.TrendingTvSeries
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ShowsScreenViewModel (
    trendingTvSeries: TrendingTvSeries,
    popularTvSeries: GetPopularSeries,
    airingToday: GetAiringToday
) : ViewModel() {

    val trendingTvSeries = trendingTvSeries()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val popularSeries = popularTvSeries()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )

    val airingToday = airingToday()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
}