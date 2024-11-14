package com.ddanddan.watch.presentation

/**
 * 이미지와 텍스트로 칼로리 값을 표시합니다.
 */
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.ddanddan.ddanddan.R
import kotlin.math.roundToInt

enum class DisplayState {
    IMAGE, TEXT
}

@Composable
fun CalorieProgressBar(
    calories: Double,
    goalCalories: Double?,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 24.dp
) {
    val displayedCalories = calories.takeIf { !it.isNaN() } ?: 0.0
    val progress = calculateProgress(displayedCalories, goalCalories)

    var displayState by remember { mutableStateOf(DisplayState.TEXT) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable {
                displayState = when (displayState) {
                    DisplayState.IMAGE -> DisplayState.TEXT
                    DisplayState.TEXT -> DisplayState.IMAGE
                }
            }
    ) {
        CircularProgressBar(progress = progress, strokeWidth = strokeWidth)

        when (displayState) {
            DisplayState.IMAGE -> {
                CalorieImage()
            }
            DisplayState.TEXT -> {
                CalorieText(displayedCalories)
            }
        }
    }
}

@Composable
fun CircularProgressBar(progress: Double, strokeWidth: Dp) {
    val colorPalette = DDanDDanColorPalette.current

    Canvas(modifier = Modifier.fillMaxSize()) {
        val sweepAngle = 360 * progress.toFloat()
        val strokePx = strokeWidth.toPx()

        drawArc(
            color = colorPalette.elevation_color_elevation_level02,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokePx)
        )

        drawArc(
            color = colorPalette.color_graphic_pink,
            startAngle = -90f, // 12시 방향 시작
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokePx, cap = StrokeCap.Butt) //모서리 둥글지 않게 설정
        )
    }
}

@Composable
fun CalorieImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_cat_pink_lev5),
        contentDescription = "Calorie Image",
        modifier = Modifier
            .fillMaxSize()
            .padding(33.dp)
    )
}

@Composable
fun CalorieText(displayedCalories: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        Text(
            text = displayedCalories.roundToInt().toString(),
            fontSize = 48.sp,
            fontFamily = NeoDgm,
            color = Color.White,
            modifier = Modifier.alignByBaseline()
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "kcal",
            fontSize = 16.sp,
            fontFamily = NeoDgm,
            color = Color.White,
            modifier = Modifier.alignByBaseline()
        )
    }
}

private fun calculateProgress(calories: Double, goal: Double?): Double {
    return when {
        goal == null || goal <= 0 -> 0.0
        else -> (calories / goal).coerceIn(0.0, 1.0)
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 200,
    heightDp = 200
)
@Composable
fun PreviewCalorieProgressBar() {
    CalorieProgressBar(
        calories = 750.0,
        goalCalories = 1000.0,
        modifier = Modifier
            .fillMaxSize()
    )
}
