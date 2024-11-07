package dev.robert.moviescatalogue.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import dev.robert.moviescatalogue.data.dto.MovieResult
import dev.robert.moviescatalogue.data.dto.PersonResult
import dev.robert.moviescatalogue.data.dto.SearchResult
import dev.robert.moviescatalogue.data.dto.TvShowResult
import dev.robert.moviescatalogue.data.toMovie
import dev.robert.moviescatalogue.domain.model.Movie
import dev.robert.moviescatalogue.domain.utils.createImageUrl
import dev.robert.moviescatalogue.presentation.components.FilledTextFilled
import dev.robert.moviescatalogue.presentation.components.NetworkImage
import dev.robert.moviescatalogue.presentation.components.SavedMovieItem
import dev.robert.moviescatalogue.presentation.components.SearchPagingUi
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SearchScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateUp: () -> Unit,
    onNavigateToDetails: (Movie) -> Unit
) {
    val viewModel = koinViewModel<SearchScreenViewModel>()
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    SearchScreenContent(
        searchResults = searchResults,
        onNavigateUp = onNavigateUp,
        onNavigateToDetails = onNavigateToDetails,
        searchQuery = searchQuery,
        onEvent = viewModel::onEvent,
        animatedVisibilityScope = animatedVisibilityScope
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SearchScreenContent(
    searchQuery: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onEvent: (SearchScreenEvents) -> Unit,
    searchResults: LazyPagingItems<SearchResult>,
    onNavigateUp: () -> Unit,
    onNavigateToDetails: (Movie) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(end = 8.dp, top = 8.dp, bottom = 8.dp)
        ) {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Search"
                )
            }
            FilledTextFilled(
                value = searchQuery,
                onValueChange = { newQuery ->
                    onEvent(SearchScreenEvents.Search(newQuery))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                maxLine = 1,
                label = "Search for a Movie, Tv show or a person.....",
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    AnimatedVisibility(visible = searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onEvent(SearchScreenEvents.ClearSearch) }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            )
        }
        SearchPagingUi(
            data = searchResults,
            searchQuery = searchQuery,
            content = {
                SearchResultItem(
                    item = it,
                    onItemClick = {},
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        )

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SearchResultItem(
    item: SearchResult,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: () -> Unit
) {
    when (item) {
        is MovieResult -> {
            SavedMovieItem(
                movie = item.toMovie(),
                animatedVisibilityScope = animatedVisibilityScope,
                transitionKey = item.posterPath +"/" + item.id
            )
        }

        is TvShowResult -> {
            SavedMovieItem(
                movie = item.toMovie(),
                animatedVisibilityScope = animatedVisibilityScope,
                transitionKey = item.posterPath +"/" + item.id
            )
        }

        is PersonResult -> {
            PersonItem(
                person = item,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun PersonItem(
    person: PersonResult,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable {

            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            NetworkImage(
                imageUrl = person.profilePath?.createImageUrl() ?: "",
                contentDescription = "Movie",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = person.name.ifEmpty { "No name" },
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = person.gender.toString(),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
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
                        text = person.popularity.toString(),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow {
                    items(person.knownFor.size) { index ->
                        val movie = person.knownFor[index]
                        Text(
                            text = movie.title,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

            }
        }
    }
}


