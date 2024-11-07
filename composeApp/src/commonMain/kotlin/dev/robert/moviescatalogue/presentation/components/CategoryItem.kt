package dev.robert.moviescatalogue.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryItem(
    category: String,
    isSelected: Boolean,
    onCategorySelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(end = 16.dp)
            .selectable(
                selected = isSelected,
                onClick = onCategorySelected
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = category,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 13.sp
        )

        val lineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)

        val animatedWidth by animateFloatAsState(
            targetValue = if (isSelected) 100f else 0f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )

        Canvas(
            modifier = Modifier
                .padding(top = 4.dp)
                .height(2.dp)
                .width(50.dp)
        ) {
            drawLine(
                color = lineColor,
                start = Offset(size.width / 2 - animatedWidth / 2, 0f),
                end = Offset(size.width / 2 + animatedWidth / 2, 0f),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}