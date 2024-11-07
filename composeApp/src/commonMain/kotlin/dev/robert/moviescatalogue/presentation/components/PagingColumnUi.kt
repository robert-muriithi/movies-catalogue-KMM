package dev.robert.moviescatalogue.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import coil3.network.HttpException
import okio.IOException

@Composable
fun <T : Any> PagingColumnUi(
    data: LazyPagingItems<T>,
    content: @Composable (T) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
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
                            modifier = Modifier.fillMaxWidth(),
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
                            modifier = Modifier.fillMaxSize(),
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
                            modifier = Modifier.fillMaxWidth(),
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