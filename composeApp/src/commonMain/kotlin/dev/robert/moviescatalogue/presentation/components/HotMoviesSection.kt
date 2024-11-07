package dev.robert.moviescatalogue.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import app.cash.paging.compose.LazyPagingItems
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.utils.createImageUrl
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TrendingMoviesHorizontalPager(
    modifier: Modifier = Modifier,
    onMovieClick: (Movie) -> Unit,
    weeksTrending: LazyPagingItems<Movie>,
) {
    val pagerState = rememberPagerState(
        pageCount = {
            weeksTrending.itemCount
        },
        initialPage = 1
    )

    LaunchedEffect(true) {
        while (true) {
            delay(3000L)
            if (pagerState.pageCount == 0) return@LaunchedEffect
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % pagerState.pageCount,
                animationSpec = tween(
                    durationMillis = 600,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .height(440.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(start = 100.dp, end = 100.dp),
        pageSize = PageSize.Fixed(220.dp)
    ) { page: Int ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier
                    .size(
                        if (pagerState.currentPage == page) 350.dp else 250.dp,
                    )
                    .padding(1.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                        scaleX = lerp(
                            start = 0.8f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleY = scaleX
                    },
                onClick = {
                    weeksTrending[page]?.let { movie ->
                        onMovieClick(movie)
                    }
                }
            ) {
                NetworkImage(
                    imageUrl = weeksTrending[page]?.posterPath?.createImageUrl() ?: "",
                    contentScale = ContentScale.Crop,
                    contentDescription = "Patch",
                    modifier =
                    Modifier
                        .fillMaxSize()

                )
            }
            AnimatedVisibility(visible = pagerState.currentPage == page) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = weeksTrending[page]?.title?.ifEmpty { weeksTrending[page]?.name }
                            ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 5.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                    )
                    Text(
                        text = weeksTrending[page]?.releaseDate?.ifEmpty { weeksTrending[page]?.firstAirDate }
                            ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Thin,
                        fontSize = 12.sp,
                        maxLines = 1,
                    )

                    Text(
                        text = if (weeksTrending[page]?.mediaType == "tv") "TV Series" else "Movie",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}