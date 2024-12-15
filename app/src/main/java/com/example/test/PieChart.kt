package com.example.test

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    data: List<Float>,
    colors: List<Color>,
    labels: List<String>,
    modifier: Modifier = Modifier
) {
    val total = data.sum()
    var startAngle = -90f

    Canvas(modifier = modifier.size(200.dp)) {
        data.forEachIndexed { index, value ->
            if (value <= 0) return@forEachIndexed

            val sweepAngle = if (total == 0f) 0f else (value / total) * 360f


            drawArc(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true
            )


            val angle = startAngle + sweepAngle / 2
            val radius = size.minDimension / 3
            val x = center.x + radius * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = center.y + radius * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()


            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    labels[index],
                    x,
                    y,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 48f
                        textAlign = android.graphics.Paint.Align.CENTER
                        typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)

                    }
                )
            }

            startAngle += sweepAngle
        }
    }
}
