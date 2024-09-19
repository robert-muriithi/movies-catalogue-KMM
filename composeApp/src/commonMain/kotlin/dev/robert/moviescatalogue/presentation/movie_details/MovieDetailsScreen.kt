package dev.robert.moviescatalogue.presentation.movie_details

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.utils.JsonConverter
import dev.robert.moviescatalogue.domain.utils.createImageUrl
import dev.robert.moviescatalogue.presentation.components.NetworkImage
import kotlin.math.absoluteValue

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    movie: String
) {
    val movieObj = JsonConverter.fromJsonString<Movie>(movie)
    MovieDetailsScreenContent(
        movie = movieObj,
        onNavigateBack = { navController.navigateUp() },
        onAddToFavorite = {}
    )
}

@Composable
fun MovieDetailsScreenContent(
    movie: Movie,
    onNavigateBack: () -> Unit,
    onAddToFavorite: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    val items = listOf(1..20).flatten()
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
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = onAddToFavorite,
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .size(
                            height = 220.dp,
                            width = 180.dp
                        )
                        .align(
                            Alignment.BottomStart
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
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth(0.5f)
//                        .height(100.dp)
//                        .background(
//                            color = Color.Black.copy(alpha = 0.7f)
//                        )
//                        .align(
//                            Alignment.BottomEnd
//                        )
//                ){
//                    Column(
//                        modifier = Modifier.fillMaxWidth()
//                            .padding(16.dp),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = movie.title,
//                            color = Color.White,
//                            style = MaterialTheme.typography.headlineMedium
//                        )
//                        Text(
//                            text = movie.releaseDate,
//                            color = Color.White,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Text(
//                            text = movie.overview,
//                            color = Color.White,
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                }
            }
        }
        item {
            items.forEach { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Item $index",
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
        }
    }
}