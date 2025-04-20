package com.thitiwut.weightpicker

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Scale(
    modifier: Modifier = Modifier,
    style: ScaleStyle = ScaleStyle(),
    minWeight: Int = 20,
    maxWeight: Int = 250,
    initialWeight: Int = 80,
    onWeightChange: (Int) -> Unit,
) {
    val radius = style.radius
    val scaleWidth = style.scaleWidth
    var center by remember { mutableStateOf(Offset.Zero) }
    var circleCenter by remember { mutableStateOf(Offset.Zero) }
    var angle by remember { mutableFloatStateOf(0f) }
    Canvas(modifier = modifier) {
        center = this.center
        circleCenter = Offset(
            center.x,
            scaleWidth.toPx() / 2f + radius.toPx()
        )

        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2f

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = Color.WHITE
                    setStyle(Paint.Style.STROKE)
                    setShadowLayer(
                        60f,
                        0f,
                        0f,
                        Color.argb(50, 0, 0, 0)
                    )
                }
            )
        }

        // Draw the scale lines
        for (i in minWeight..maxWeight) {
            val angleInRad = (i - initialWeight + angle - 90) * (PI / 180f).toFloat()
            val lineType = when {
                i % 10 == 0 -> LineType.TenStep
                i % 5 == 0 -> LineType.FiveStep
                else -> LineType.Normal
            }
            val lineLength = when (lineType) {
                LineType.TenStep -> style.tenStepLineLength.toPx()
                LineType.FiveStep -> style.fiveStepLineLength.toPx()
                LineType.Normal -> style.normalLineLength.toPx()
            }
            val lineColor = when (lineType) {
                LineType.TenStep -> style.tenStepLineColor
                LineType.FiveStep -> style.fiveStepLineColor
                LineType.Normal -> style.normalLineColor
            }
            val lineStart = Offset(
                x = (outerRadius - lineLength) * cos(angleInRad) + circleCenter.x,
                y = (outerRadius - lineLength) * sin(angleInRad) + circleCenter.y
            )
            val lineEnd = Offset(
                x = outerRadius * cos(angleInRad) + circleCenter.x,
                y = outerRadius * sin(angleInRad) + circleCenter.y
            )

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScalePreview() {
    Scale(
        modifier = Modifier.fillMaxSize(),
        style = ScaleStyle(),
        minWeight = 20,
        maxWeight = 250,
        initialWeight = 80,
        onWeightChange = {}
    )
}