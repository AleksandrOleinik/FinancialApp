package com.example.test

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.format.DateTimeFormatter


//@Preview(showBackground = true)
@Composable
fun LineChartScreen(incomeData: List<Income>) {

    val processedData = incomeData.map {
        Pair(it.date.format(DateTimeFormatter.ofPattern("MM/yy")), it.amount.toFloat())
    }

    AndroidView(
        modifier = Modifier
            .height(300.dp)
            .width(300.dp),
        factory = { context ->
            LineChart(context).apply {
                axisRight.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    labelRotationAngle = -45f
                }
                axisLeft.apply {
                    setDrawGridLines(true)
                }
                setTouchEnabled(false)
                setPinchZoom(false)
            }
        },
        update = { lineChart ->

            val entries = processedData.mapIndexed { index, pair ->
                Entry(index.toFloat(), pair.second)
            }


            val dataSet = LineDataSet(entries, "Income").apply {
                color = Color.GREEN
                setCircleColor(Color.GREEN)
                lineWidth = 2f
                circleRadius = 5f
                setDrawFilled(true)
                fillColor = Color.GREEN
                mode = LineDataSet.Mode.LINEAR
            }


            lineChart.data = LineData(dataSet)


            lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(processedData.map { it.first })


            lineChart.invalidate()
        }
    )
}

