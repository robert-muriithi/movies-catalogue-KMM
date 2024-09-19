package dev.robert.moviescatalogue.presentation.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.PagingData
import dev.robert.moviescatalogue.domain.usecase.TrendingTvSeries
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ShowsScreenViewModel (
    trendingTvSeries: TrendingTvSeries
) : ViewModel() {

    val trendingTvSeries = trendingTvSeries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty()
        )
}