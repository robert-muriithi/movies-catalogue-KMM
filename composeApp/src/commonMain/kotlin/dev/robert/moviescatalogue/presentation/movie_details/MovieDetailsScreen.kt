package dev.robert.moviescatalogue.presentation.movie_details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import dev.robert.moviescatalogue.domain.model.Genre
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.model.MovieCast
import dev.robert.moviescatalogue.domain.model.MovieReview
import dev.robert.moviescatalogue.domain.utils.JsonConverter
import dev.robert.moviescatalogue.domain.utils.createImageUrl
import dev.robert.moviescatalogue.domain.utils.formatDate
import dev.robert.moviescatalogue.presentation.components.MovieItem
import dev.robert.moviescatalogue.presentation.components.NetworkImage
import dev.robert.moviescatalogue.presentation.components.PagingColumnUi
import dev.robert.moviescatalogue.presentation.components.PagingRowUi
import dev.robert.moviescatalogue.presentation.components.ReviewItem
import kotlinx.serialization.builtins.ListSerializer
import movies_catalogue.composeapp.generated.resources.Res
import movies_catalogue.composeapp.generated.resources.bookmark_outline
import movies_catalogue.composeapp.generated.resources.bookmark_filled
import movies_catalogue.composeapp.generated.resources.placeholder
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieDetailsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    jsonString: String,
    onMovieClick: (Movie) -> Unit,
    onNavigateToReviews: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToSimilar: (Int, String) -> Unit
) {
    val viewModel: MovieDetailsViewModel = koinViewModel()
    val movieObj = JsonConverter.fromJsonString<Movie>(jsonString)

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(
            MovieDetailEvent.GetMovieDetails(
                movieObj.id,
                mediaType = movieObj.mediaType
            )
        )
        viewModel.onEvent(
            MovieDetailEvent.GetSimilarMovies(
                movieObj.id,
                mediaType = movieObj.mediaType
            )
        )
        viewModel.onEvent(
            MovieDetailEvent.GetMovieReviews(
                movieObj.id,
                mediaType = movieObj.mediaType
            )
        )
    }

    val movieDetailState by viewModel.movieDetail.collectAsStateWithLifecycle()
    val reviews = viewModel.movieReviews.collectAsLazyPagingItems()
    val similarMovies = viewModel.similarMovies.collectAsLazyPagingItems()
    MovieDetailsScreenContent(
        movie = movieObj,
        onNavigateBack = onNavigateBack,
        onEvent = { viewModel.onEvent(it) },
        movieDetailState = movieDetailState,
        reviews = reviews,
        similarMovies = similarMovies,
        animatedVisibilityScope = animatedVisibilityScope,
        modifier = modifier,
        onMovieClick = onMovieClick,
        onNavigateToReviews = onNavigateToReviews,
        onNavigateToSimilar = onNavigateToSimilar,
        mediaType = movieObj.mediaType,
        movieId = movieObj.id
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieDetailsScreenContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
    mediaType: String,
    movieId: Int,
    movieDetailState: MovieDetailState,
    similarMovies: LazyPagingItems<Movie>,
    movie: Movie,
    onNavigateToSimilar: (Int, String) -> Unit,
    reviews: LazyPagingItems<MovieReview>?,
    onEvent: (MovieDetailEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToReviews: (String) -> Unit
) {

    val lazyListState = rememberLazyListState()
    val scrolledY = 0f

    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .sharedBounds(
                sharedContentState = rememberSharedContentState(key = movie.posterPath + "/" + movie.id),
                animatedVisibilityScope = animatedVisibilityScope,
            ),
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
                            alpha = lerp(0.5f, 1f, (scrolledY / 200).coerceIn(0f, 1f))
                            scaleX = lerp(1f, 1.5f, (scrolledY / 200).coerceIn(0f, 1f))
                            scaleY = lerp(1f, 1.5f, (scrolledY / 200).coerceIn(0f, 1f))
                        }
                ) {
                    AsyncImage(
                        model = if (movie.posterPath.isEmpty()) "https://pixy.org/src/30/302909.png"
                        else movie.posterPath.createImageUrl(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .align(
                            Alignment.TopStart
                        )
                        .padding(top = 16.dp),
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
                        onClick = {
                            onEvent(MovieDetailEvent.AddToSaved(movie))
                        },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(resource = if (movieDetailState.isSaved) Res.drawable.bookmark_filled else Res.drawable.bookmark_outline),
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
                                scaleX = lerp(1f, 1f, (scrolledY / 100).coerceIn(0f, 1f))
                                scaleY = lerp(1f, 1f, (scrolledY / 100).coerceIn(0f, 1f))
                            },

                        ) {
                        NetworkImage(
                            imageUrl = if (movie.posterPath.isEmpty()) "https://pixy.org/src/30/302909.png"
                            else movie.posterPath.createImageUrl(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .background(
                                MaterialTheme.colorScheme.surface
                            )
                            .weight(1f)
                            .padding(end = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = movie.title.ifEmpty { movie.name.ifEmpty { "No Title" } } + " (${movie.mediaType})",
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
                                    text = movie.releaseDate.ifEmpty { movie.firstAirDate }
                                        .ifEmpty { "Unknown date" },
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
            OverviewSection(
                movie = movie,
                movieDetailState = movieDetailState,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            CastSection(
                credits = movieDetailState.credits,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            SimilarTab(
                animatedVisibilityScope = animatedVisibilityScope,
                similarMovies = similarMovies,
                onMovieClick = onMovieClick,
                onNavigateToSimilar = onNavigateToSimilar,
                modifier = Modifier
                    .fillMaxWidth(),
                mediaType = mediaType,
                movieId = movieId
            )
        }
        item {
            if (reviews?.itemCount!! > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reviews",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    TextButton(
                        onClick = {
                            val obj = JsonConverter.toJsonString(reviews.itemSnapshotList.items, ListSerializer(MovieReview.serializer()))
                            onNavigateToReviews(obj)
                        },
                        content = {
                            Text(
                                text = "View All",
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Thin
                                )
                            )
                        }
                    )
                }
            }
        }
        reviews?.itemCount?.let {
            if (it > 0) items(1) { index ->
                val review = reviews[index]
                ReviewItem(review)
            }
        }
    }
}


@Composable
private fun CastSection(
    credits: List<MovieCast>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Cast",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(credits.size) { index ->
                val cast = credits[index]
                Column(
                    modifier = Modifier
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NetworkImage(
                        imageUrl = if (cast.profilePath.isEmpty()) "https://pixy.org/src/30/302909.png"
                        else cast.profilePath.createImageUrl(),
                        contentDescription = cast.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = cast.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SimilarTab(
    animatedVisibilityScope: AnimatedVisibilityScope,
    mediaType: String,
    movieId: Int,
    similarMovies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit,
    onNavigateToSimilar: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Similar",
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = {
                    onNavigateToSimilar(movieId, mediaType)
                },
                content = {
                    Text(
                        text = "View All",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Thin
                        )
                    )
                }
            )
        }
        PagingRowUi(
            data = similarMovies,
            content = { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClick = onMovieClick,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = movie.posterPath + "/" + movie.id
                )
            }
        )
    }
}


@Composable
fun ReviewsTab(
    modifier: Modifier = Modifier,
    reviews: LazyPagingItems<MovieReview>?
) {

    LazyColumn(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {

        }
        reviews?.let { reviews ->

        }
    }
}


@Composable
fun OverviewSection(
    movie: Movie,
    movieDetailState: MovieDetailState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Overview",
            color = MaterialTheme.colorScheme.onSurface,
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
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Column {
                Text(
                    text = "Release Date",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = movie.releaseDate.ifEmpty { movie.firstAirDate }
                        .ifEmpty { "Unknown date" },
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                )

            }
            Column {
                movieDetailState.movieDetails.genres?.let { genres ->
                    Text(
                        text = "Genres",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    GenresRow(
                        genres = genres,
                        modifier = Modifier
                            .padding(2.dp)
                            .background(
                                MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.5f),
                                MaterialTheme.shapes.small
                            ).padding(4.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun GenresRow(
    genres: List<Genre>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        content = {
            items(genres.size) { index ->
                Box(
                    modifier = modifier

                ) {
                    Text(
                        text = genres[index].name,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                    )
                }
            }
        },
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    )
}