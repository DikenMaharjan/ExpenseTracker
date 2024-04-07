package com.example.moneytrack.home.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.example.moneytrack.ui.theme.LocalDimens
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.edges.rememberFadingEdges
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.chart.zoom.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.m3.theme.rememberM3VicoTheme
import com.patrykandpatrick.vico.compose.theme.ProvideVicoTheme
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.values.AxisValueOverrider
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.component.shape.shader.ColorShader
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun WeeklyExpenseGraph(
    modifier: Modifier = Modifier,
    modelProducer: CartesianChartModelProducer,
    orderedDays: List<DayOfWeek>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.dimen12)
    ) {
        Text(text = "Weekly Expense", style = MaterialTheme.typography.titleMedium)
        WeeklyChart(
            modelProducer = modelProducer,
            modifier = Modifier
                .height(LocalDimens.current.dimen200)
                .fillMaxWidth(),
            orderedDays = orderedDays
        )
    }

}

@Composable
private fun WeeklyChart(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier,
    orderedDays: List<DayOfWeek>
) {
    ProvideVicoTheme(theme = rememberM3VicoTheme()) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    lines = listOf(
                        rememberLineSpec(shader = ColorShader(MaterialTheme.colorScheme.primary.toArgb()))
                    ),
                    axisValueOverrider = axisValueOverrider,
                ),
                startAxis = rememberStartAxis(
                    guideline = null,
                    horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Outside
                ),
                bottomAxis = rememberBottomAxis(
                    valueFormatter = { value: Float, _: ChartValues, _: AxisPosition.Vertical? ->
                        val index = ((((value - 1) % 7) + 7) % 7).toInt()
                        orderedDays[index].getDisplayName(
                            TextStyle.SHORT, Locale.getDefault()
                        )
                    }
                ),
                fadingEdges = rememberFadingEdges(),
            ),
            modelProducer = modelProducer,
            modifier = modifier,
            marker = null,
            runInitialAnimation = false,
            horizontalLayout = HorizontalLayout.fullWidth(),
            zoomState = rememberVicoZoomState(zoomEnabled = false),
            scrollState = rememberVicoScrollState(scrollEnabled = false)
        )
    }
}


private val axisValueOverrider = AxisValueOverrider.adaptiveYValues(yFraction = 1.2f, round = true)

