package com.example.test

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
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


//@Preview(showBackground = true)
@Composable
fun LineChartScreen() {

    AndroidView(
        modifier = Modifier.height(300.dp)
            .width(300.dp),
        factory = { context ->
            LineChart(context).apply {
                setupLineChart(this, getSampleData())
            }
        }
    )
}

fun getSampleData(): List<Pair<String, Float>> {
    return listOf(
        Pair("12/22", 50f),
        Pair("04/24", 70f),
        Pair("05/24", 85f),
        Pair("08/24", 100f),
        Pair("09/24", 120f),
        Pair("10/24", 140f),
        Pair("11/24", 160f)
    )
}


fun setupLineChart(lineChart: LineChart, data: List<Pair<String, Float>>) {

    val entries = data.mapIndexed { index, pair ->
        Entry(index.toFloat(), pair.second)
    }

    val dataSet = LineDataSet(entries, "Values").apply {
        color = Color.GREEN
        setCircleColor(Color.GREEN)
        lineWidth = 2f
        circleRadius = 5f
        setDrawFilled(true)
        fillColor = Color.GREEN
        mode = LineDataSet.Mode.LINEAR
    }

    lineChart.data = LineData(dataSet)


    lineChart.axisRight.isEnabled = false
    lineChart.description.isEnabled = false
    lineChart.legend.isEnabled = false


    lineChart.xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM
        setDrawGridLines(false)
        valueFormatter = IndexAxisValueFormatter(data.map { it.first })
        labelRotationAngle = -45f
    }


    lineChart.axisLeft.apply {
        setDrawGridLines(true)
    }


    lineChart.setTouchEnabled(false)
    lineChart.setPinchZoom(false)


    lineChart.invalidate()
}
