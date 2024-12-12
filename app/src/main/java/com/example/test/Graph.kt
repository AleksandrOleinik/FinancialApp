package com.example.test


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun LineChartScreen(
    data: List<Pair<String, Float>>,
    label: String,
    lineColor: Int
) {
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
                    xAxis.setLabelCount(data.size, false)
                    granularity = 1f
                    isGranularityEnabled = true
                }
                axisLeft.apply {
                    setDrawGridLines(true)
                }
                setTouchEnabled(true)
                setPinchZoom(true)
            }
        },
        update = { lineChart ->

            val entries = data.mapIndexed { index, pair ->
                Entry(index.toFloat(), pair.second)
            }


            val dataSet = LineDataSet(entries, label).apply {
                color = lineColor
                setCircleColor(lineColor)
                lineWidth = 2f
                circleRadius = 5f
                setDrawFilled(true)
                fillColor = lineColor
                mode = LineDataSet.Mode.LINEAR
            }


            lineChart.data = LineData(dataSet)


            lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(data.map { it.first })


            lineChart.invalidate()
        }
    )
}
