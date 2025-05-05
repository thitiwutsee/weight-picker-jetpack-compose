package com.thitiwut.weightpicker

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ClockScreen() {

    val radius = 150.dp
    val milliSecond = remember {
        System.currentTimeMillis()
    }
    val seconds = remember {
        mutableFloatStateOf(milliSecond / 1000f % 60f)
    }
    val minutes = remember {
        mutableFloatStateOf(milliSecond / 1000f % 3600f / 60f)
    }
    val hours = remember {
        mutableFloatStateOf(milliSecond / 1000f % 86400f / 3600f)
    }

    LaunchedEffect(seconds) {
        delay(1000L)
        seconds.floatValue += 1f
        minutes.floatValue += 1f / 60f
        hours.floatValue += 1f / 3600f
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(radius * 2f)
        ) {

            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    center.x,
                    center.y,
                    radius.toPx(),
                    Paint().apply {
                        color = Color.WHITE
                        setShadowLayer(
                            10f,
                            0f,
                            0f,
                            Color.GRAY
                        )
                    }
                )
            }

            for (i in 0..59) {
                val angleInRed = (i * 6f) * (PI.toFloat() / 180f)
                val lineLength = if (i % 5 == 0) {
                    20.dp.toPx()
                } else {
                    10.dp.toPx()
                }
                val color = if (i % 5 == 0) {
                    androidx.compose.ui.graphics.Color.Black
                } else {
                    androidx.compose.ui.graphics.Color.LightGray
                }
                val lineStart = Offset(
                    x = radius.toPx() * cos(angleInRed) + center.x, //center.x + radius.toPx() * cos(Math.toRadians(angle.toDouble())).toFloat(),
                    y = radius.toPx() * sin(angleInRed) + center.x//center.y + radius.toPx() * sin(Math.toRadians(angle.toDouble())).toFloat()
                )
                val lineEnd = Offset(
                    x = (radius.toPx() - lineLength) * cos(angleInRed) + center.x,
                    y = (radius.toPx() - lineLength) * sin(angleInRed) + center.y
                )
                drawLine(
                    color = color,
                    start = lineStart,
                    end = lineEnd,
                    strokeWidth = 5f
                )
            }

            rotate(degrees = seconds.floatValue * 6f) {
                drawLine(
                    color = androidx.compose.ui.graphics.Color.Red,
                    start = center,
                    end = Offset(
                        center.x, 100.dp.toPx()
                    ),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            rotate(degrees = minutes.floatValue * 6f) {
                drawLine(
                    color = androidx.compose.ui.graphics.Color.Cyan,
                    start = center,
                    end = Offset(
                        center.x, 50.dp.toPx()
                    ),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            rotate(degrees = hours.floatValue * 6f) {
                drawLine(
                    color = androidx.compose.ui.graphics.Color.Black,
                    start = center,
                    end = Offset(
                        center.x, 60.dp.toPx()
                    ),
                    strokeWidth = 5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClockPreview() {
    ClockScreen()
}