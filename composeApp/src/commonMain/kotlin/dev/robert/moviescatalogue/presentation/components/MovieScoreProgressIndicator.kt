package dev.robert.moviescatalogue.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MovieScoreProgressIndicator(
    modifier: Modifier = Modifier,
    score : Float = 0f,
    animationDuration: Int = 1000,
) {
    // Circular Progress Indicator indicating the movie Score
    // Movie Score is a value between 0% and 100%
    // The color of the progress indicator should be green if the score is above 70%
    // The color of the progress indicator should be yellow if the score is between 50% and 70%
    // The color of the progress indicator should be red if the score is below 50%
    val foregroundIndicatorColor = when {
        score > 70f -> Color.Green
        score in 50f..70f -> Color.Yellow
        else -> Color.Red
    }

    val movieScoreAnimate = animateFloatAsState(
        targetValue = score,
        animationSpec = tween(
            durationMillis = animationDuration
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(50.dp)
            .padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(50.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val center = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
            val radius = size.minDimension / 2
            val startAngle = 270f
            val sweepAngle = 360 * movieScoreAnimate.value / 100
            val useCenter = false
            val style = Stroke(
                width = 10f,
                cap = StrokeCap.Round
            )
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = useCenter,
                style = style,
                size = Size(radius, radius),
                topLeft = center
            )
        }
    }
}

@Composable
fun CircularProgressbar(
    size: Dp = 40.dp,
    indicatorThickness: Dp = 2.dp,
    dataUsage: Float = 80f,
    animationDuration: Int = 1000,
    modifier: Modifier = Modifier
) {
    val foregroundIndicatorColor = when {
        dataUsage > 70f -> Color.Green
        dataUsage in 50f..70f -> Color.Yellow
        dataUsage in 30f..50f -> Color.Yellow.copy(alpha = 0.5f)
        else -> Color.Red
    }

    var dataUsageRemember by remember {
        mutableFloatStateOf(-1f)
    }

    val dataUsageAnimate = animateFloatAsState(
        targetValue = dataUsageRemember,
        animationSpec = tween(
            durationMillis = animationDuration
        ), label = ""
    )

    LaunchedEffect(Unit) {
        dataUsageRemember = dataUsage
    }

    val circleColor = MaterialTheme.colorScheme.tertiaryContainer
    Box(
        modifier = Modifier
            .size(size)
            .padding(top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val circleRadius = size.toPx() / 2
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.Transparent, circleColor),
                    center = Offset(x = size.toPx() / 2, y = size.toPx() / 2),
                    radius = circleRadius
                ),
                radius = circleRadius,
                center = Offset(x = size.toPx() / 2, y = size.toPx() / 2)
            )

            val sweepAngle = (dataUsageAnimate.value) * 360 / 100

            val arcSize = size.toPx() - indicatorThickness.toPx()
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
                size = Size(arcSize, arcSize),
                topLeft = Offset(
                    x = (indicatorThickness.toPx() / 2),
                    y = (indicatorThickness.toPx() / 2)
                )
            )
        }

        DisplayText(
            animateNumber = dataUsageAnimate,
            dataTextStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.surface,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            ),
        )
    }

}

@Composable
private fun DisplayText(
    animateNumber: State<Float>,
    dataTextStyle: TextStyle,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = (animateNumber.value).toInt().toString() + "%",
            style = dataTextStyle,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}