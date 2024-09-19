package dev.robert.moviescatalogue.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.robert.moviescatalogue.data.api.dto.MovieResponse
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.utils.createImageUrl

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier =  modifier
            .height(180.dp)
            .width(130.dp)
            .padding(horizontal = 3.dp),
        onClick = {
            onMovieClick(movie)
        }
    ) {
        NetworkImage(
            imageUrl = movie.posterPath.createImageUrl(),
            contentScale = ContentScale.Crop,
            contentDescription = "Movie",
            modifier =
            Modifier
                .fillMaxSize()
        )
    }
}