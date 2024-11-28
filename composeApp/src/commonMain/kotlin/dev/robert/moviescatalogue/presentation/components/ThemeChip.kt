package dev.robert.moviescatalogue.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ThemeToggleChips(
    themeOptions: List<ThemeOption>,
    onSelectTheme: (Theme) -> Unit,
    selectedTheme: Theme,
    modifier: Modifier = Modifier
) {
    // val selectedTheme = remember { mutableStateOf(theme.themeFromValue()) }
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        themeOptions.let { cat ->
            items(cat.size) { index ->
                ThemeChip(
                    text = cat[index].theme.name,
                    onClick = {
//                        selectedTheme.value = cat[index].theme.themeFromValue()
                        onSelectTheme(cat[index].theme)
                    },
                    selected = cat[index].theme == selectedTheme,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun ThemeChip(
    text: String,
    onClick: (String) -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .border(
            width = 0.dp,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            shape = MaterialTheme.shapes.small
        )
        .background(
            color = if (selected) {
                MaterialTheme.colorScheme.tertiaryContainer
            } else {
                Color.Transparent
            },
            shape = MaterialTheme.shapes.small
        )
        .padding(10.dp)
        .clickable {
            onClick(text)
        }
    ) {
        Text(
            text = text.toUpperCase(),
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(800)),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 1.dp)
        )
    }
}

data class ThemeOption(
    val theme: Theme,
)

enum class Theme {
    LIGHT,
    DARK,
    SYSTEM,
    MATERIAL_YOU
}