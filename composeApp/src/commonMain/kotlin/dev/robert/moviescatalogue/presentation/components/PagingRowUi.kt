package dev.robert.moviescatalogue.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import coil3.network.HttpException
import okio.IOException

@Composable
fun <T : Any> PagingRowUi(
    data: LazyPagingItems<T>,
    content: @Composable (T) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        items(data.itemCount) { index ->
            val item = data[index]
            item?.let { content(it) }
        }

        data.loadState.apply {
            when {
                refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                CircularProgressIndicator(
                                    strokeWidth = 2.dp,
                                )
                            }
                        }
                    }
                }

                refresh is LoadState.NotLoading && data.itemCount < 1 -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = "No data available",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
                refresh is LoadState.Error -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                val errorMsg = when ((refresh as LoadState.Error).error) {
                                    is HttpException -> {
                                        "Oops, something went wrong!"
                                    }

                                    is IOException -> {
                                        "Couldn't reach server, check your internet connection!"
                                    }

                                    else -> {
                                        "Unknown error occurred"
                                    }
                                }
                                ErrorView(
                                    message = errorMsg,
                                    onClickRetry = { data.retry() }
                                )
                            }
                        }
                    }
                }
                append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.Center),
                                strokeWidth = 2.dp,
                            )
                        }
                    }
                }

                append is LoadState.Error -> {
                    val errorMsg = when ((append as LoadState.Error).error) {
                        is HttpException -> {
                            "Oops, something went wrong!"
                        }

                        is IOException -> {
                            "Couldn't reach server, check your internet connection!"
                        }

                        else -> {
                            "Unknown error occurred"
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ){
                            ErrorItem(
                                message = errorMsg,
                                onClickRetry = { data.retry() }
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}

@Composable
fun ErrorView(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp).onPlaced { _ ->
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Red
        )
        OutlinedButton(
            onClick = onClickRetry, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(text = "Try again")
        }
    }
}