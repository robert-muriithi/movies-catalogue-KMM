package dev.robert.moviescatalogue.presentation.movie_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.model.MovieCast
import dev.robert.moviescatalogue.domain.model.MovieReview
import dev.robert.moviescatalogue.domain.utils.JsonConverter
import dev.robert.moviescatalogue.domain.utils.createImageUrl
import dev.robert.moviescatalogue.presentation.components.MovieItem
import dev.robert.moviescatalogue.presentation.components.NetworkImage
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    movie: String
) {
    val viewModel : MovieDetailsViewModel = koinViewModel()
    val movieObj = JsonConverter.fromJsonString<Movie>(movie)

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(MovieDetailEvent.GetMovieDetails(movieObj.id))
    }

    val movieDetailState by viewModel.movieDetail.collectAsStateWithLifecycle()
    val reviews = viewModel.movieReviews.collectAsLazyPagingItems()
    val similarMovies = viewModel.similarMovies.collectAsLazyPagingItems()
    MovieDetailsScreenContent(
        movie = movieObj,
        onNavigateBack = { navController.navigateUp() },
        onEvent = { viewModel.onEvent(it) },
        movieDetailState = movieDetailState,
        reviews = reviews,
        similarMovies = similarMovies,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsScreenContent(
    movieDetailState : MovieDetailState,
    similarMovies: LazyPagingItems<Movie>,
    movie: Movie,
    reviews: LazyPagingItems<MovieReview>?,
    onEvent: (MovieDetailEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    val tabs = listOf(
        "Overview",
        "Reviews",
        "Credits",
        "Similar"
    )

    val pageState = rememberPagerState(pageCount = {tabs.size})
    val scope = rememberCoroutineScope()
    LazyColumn(
        state = lazyListState,
    ) {
        item {
            Box(
                modifier = Modifier
                    .height(450.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                        .graphicsLayer {
                            scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                            translationY = scrolledY * 0.5f
                            previousOffset = lazyListState.firstVisibleItemScrollOffset

                            alpha = lerp(0.5f, 1f, (scrolledY / 200).coerceIn(0f, 1f))
                            scaleX = lerp(1f, 1.5f, (scrolledY / 200).coerceIn(0f, 1f))
                            scaleY = lerp(1f, 1.5f, (scrolledY / 200).coerceIn(0f, 1f))
                        }
                ) {
                    AsyncImage(
                        model = movie.posterPath.createImageUrl(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .align(
                            Alignment.TopStart
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick =  {
                            onEvent(MovieDetailEvent.AddToSaved(movie))
                        },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = if (movieDetailState.isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .align(
                            Alignment.BottomStart
                        ),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Card(
                        modifier = Modifier
                            .size(
                                height = 220.dp,
                                width = 180.dp
                            )

                            .padding(start = 16.dp)
                            .graphicsLayer {
                                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                translationY = scrolledY * 0.5f
                                previousOffset = lazyListState.firstVisibleItemScrollOffset

                                scaleX = lerp(1f, 1f, (scrolledY / 100).coerceIn(0f, 1f))
                                scaleY = lerp(1f, 1f, (scrolledY / 100).coerceIn(0f, 1f))
                            },

                        ) {
                        NetworkImage(
                            imageUrl = movie.posterPath.createImageUrl(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                            modifier =
                            Modifier
                                .fillMaxSize()

                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 16.dp)
                            .graphicsLayer {
                                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                translationY = scrolledY * 0.1f
                                previousOffset = lazyListState.firstVisibleItemScrollOffset
                            }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = movie.title.ifEmpty { movie.originalTitle.ifEmpty { "No Title" } },
                                color = Color.White,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .size(11.dp)
                                )
                                Text(
                                    text = movie.voteAverage.toString(),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 11.sp
                                )

                                Text(
                                    text = " (${movie.voteCount} votes)",
                                    color = Color.White,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = " | ",
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                                Text(
                                    text = movie.releaseDate.ifEmpty { "Unknown date" },
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }
        item {
            TabRow(
                selectedTabIndex = pageState.currentPage,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            tabPositions[pageState.currentPage]
                        ),
                        height = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 8.dp, horizontal = 16.dp),

            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pageState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pageState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                state = pageState,
            ) { page ->
                when (page) {
                    0 -> OverviewTab(movie, movieDetailState)
                    1 -> ReviewsTab(reviews = reviews)
                    2 -> CastTab(
                        credits = movieDetailState.credits,
                    )
                    3 -> SimilarTab(
                        similarMovies = similarMovies
                    )
                }
            }
        }
    }
}
@Composable
private fun CastTab(
    credits: List<MovieCast>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .height(300.dp)
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        items(credits.size) { index ->
            val cast = credits[index]
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = cast.profilePath.createImageUrl(),
                    contentDescription = cast.name,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = cast.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = cast.character,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp),
                    )
                }
            }
        }
    }
}
@Composable
fun SimilarTab(
    similarMovies: LazyPagingItems<Movie>?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        similarMovies?.let { movies ->
            items(movies.itemCount) { index ->
                movies[index]?.let {
                    MovieItem(
                        movie = it,
                        onMovieClick = {}
                    )
                }
            }
        }
    }
}


@Composable
fun ReviewsTab(
    modifier: Modifier = Modifier,
    reviews: LazyPagingItems<MovieReview>?
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        reviews?.let { reviews ->
            items(reviews.itemCount) { index ->
                val review = reviews[index]
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = review?.author ?: "Unknown",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = review?.content ?: "No content",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp),
                    )
                }
            }
        }
    }
}


@Composable
fun OverviewTab(movie: Movie, movieDetailState: MovieDetailState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Overview",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = movie.overview,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp),
        )
        Text(
            text = "Genres",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        movieDetailState.movieDetails.genres?.let { genres ->
            LazyRow(
                modifier = Modifier.fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 8.dp),
            ) {
                items(genres.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp, top = 8.dp)
                            .wrapContentWidth()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.small
                            ),
                    ) {
                        Text(
                            text = genres[index].name,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
        movieDetailState.movieDetails.originalTitle?.let {
            Text(
                text = "Original Title",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = it,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
            )
        }
        movieDetailState.movieDetails.releaseDate?.let {
            Text(
                text = "Release Date",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = it,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
            )
        }
        movieDetailState.movieDetails.runtime?.let {
            Text(
                text = "Runtime",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$it minutes",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
            )
        }
        movieDetailState.movieDetails.status?.let {
            Text(
                text = "Status",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = it,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
            )
        }
        movieDetailState.movieDetails.tagline?.let {
            Text(
                text = "Tagline",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = it,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
            )
        }
        movieDetailState.movieDetails.originalLanguage?.let {
            Text(
                text = "Original Language",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = it,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
            )
        }
    }
}
