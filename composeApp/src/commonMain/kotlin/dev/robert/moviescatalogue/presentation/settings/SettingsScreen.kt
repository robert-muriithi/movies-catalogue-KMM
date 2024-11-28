package dev.robert.moviescatalogue.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val currentTheme by viewModel.theme.collectAsStateWithLifecycle()
    SettingsContent(
        modifier = modifier,
        currentTheme = currentTheme,
        onClick = viewModel::onThemeChange
    )
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    currentTheme: Boolean,
    onClick: (Boolean) -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)
    ){
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
//        Spacer(modifier = Modifier.height(36.dp))
//        Text("App Theme")
//        Spacer(modifier = Modifier.height(8.dp))
//        LazyRow(modifier = Modifier.fillMaxWidth()){
//            items(listOf("Light", "Dark")) { theme ->
//                Button(
//                    onClick = {
//                        onClick(theme == "Dark")
//                    },
//                    modifier = Modifier.padding(end = 8.dp)
//                ) {
//                    Text(theme)
//                }
//            }
//        }
    }
}