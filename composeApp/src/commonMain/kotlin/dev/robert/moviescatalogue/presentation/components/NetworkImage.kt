package dev.robert.moviescatalogue.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import movies_catalogue.composeapp.generated.resources.Res
import movies_catalogue.composeapp.generated.resources.placeholder
import org.jetbrains.compose.resources.painterResource

@Composable
fun NetworkImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale,
    contentDescription: String? = null,
) {
    SubcomposeAsyncImage(
        imageLoader = getAsyncImageLoader(LocalPlatformContext.current),
        model =
        ImageRequest
            .Builder(LocalPlatformContext.current)
            .data( imageUrl)
            .build(),
        loading = {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(32.dp),
                )
            }
        },
        error =  {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                   imageVector = Icons.Default.Info, contentDescription = "Error loading image",
                )
            }
        },
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
    )
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader
        .Builder(context)
        .crossfade(true)
        .logger(DebugLogger())
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()
