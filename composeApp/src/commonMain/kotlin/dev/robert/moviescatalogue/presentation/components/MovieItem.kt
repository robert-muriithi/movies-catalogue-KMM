package dev.robert.moviescatalogue.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.utils.createImageUrl
import kotlin.math.roundToInt

@Composable
fun MovieItem(
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
                imageUrl = movie.posterPath.createImageUrl(),
                contentScale = ContentScale.Crop,
                contentDescription = "Movie",
                modifier = Modifier.fillMaxSize()
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
