package dev.robert.moviescatalogue.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.utils.createImageUrl
import kotlin.math.roundToInt

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MovieItem(
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionKey: String,
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(150.dp)
            .height(200.dp)
            .padding(3.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            onClick = {
                onMovieClick(movie)
            }
        ) {
            NetworkImage(
                imageUrl = if (movie.posterPath.isEmpty()) "https://pixy.org/src/30/302909.png"
                else movie.posterPath.createImageUrl(),
                contentScale = ContentScale.Crop,
                contentDescription = "Movie",
                modifier = Modifier.fillMaxSize()
                    .sharedElement(
                        state = rememberSharedContentState(
                            key = sharedTransitionKey
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
            )
        }
        CircularProgressbar(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 40.dp),
            dataUsage = (movie.voteAverage.toFloat() * 10).roundToInt().toFloat(),
        )
    }
}
