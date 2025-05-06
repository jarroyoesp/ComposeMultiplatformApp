package com.jarroyo.feature.electricity.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.jarroyo.feature.electricity.ui.ElectricityContract.Effect
import com.jarroyo.feature.electricity.ui.ElectricityContract.Event
import com.jarroyo.feature.electricity.ui.ElectricityContract.State
import com.jarroyo.library.ui.shared.component.LocalMainScaffoldPadding
import com.jarroyo.library.ui.shared.theme.Spacing
import com.kizitonwose.calendar.core.now
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.line.LinePlot
import io.github.koalaplot.core.style.LineStyle
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xygraph.CategoryAxisModel
import io.github.koalaplot.core.xygraph.DefaultPoint
import io.github.koalaplot.core.xygraph.FloatLinearAxisModel
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.XYGraphScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ElectricityScreen(viewModel: ElectricityViewModel = koinViewModel<ElectricityViewModel>()) {
    ElectricityScreen(
        state = viewModel.viewState.value,
        sendEvent = { viewModel.onUiEvent(it) },
        effectFlow = viewModel.effect,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ElectricityScreen(
    state: State,
    effectFlow: Flow<Effect>,
    sendEvent: (event: Event) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(effectFlow) {
        effectFlow.onEach { effect ->
            when (effect) {
                is Effect.ShowSnackbar -> launch {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }.collect()
    }
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    Scaffold(
        topBar = { TopAppBar(sendEvent, state) },
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.padding(LocalMainScaffoldPadding.current.value),
    ) { scaffoldPadding ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.loading,
            onRefresh = {
                sendEvent(Event.OnSwipeToRefresh)
            },
        )
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .pullRefresh(pullRefreshState),
        ) {
            PullRefreshIndicator(
                state.loading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter),
            )
            Column(
                modifier = Modifier.padding(Spacing.x02),
                verticalArrangement = Arrangement.spacedBy(Spacing.x02),
            ) {
                Text(LocalDate.now().toString())
                XYSamplePlot(state)
            }
        }
    }
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
@Suppress("MagicNumber")
private fun ColumnScope.XYSamplePlot(state: State) {
    var minValue: Double = 0.0
    var maxValue: Double = 0.0
    var hourMin = ""
    var hourMax = ""
    state.electricityData?.included?.firstOrNull()?.attributes?.values?.run {
        minValue = minOf { it.value }
        maxValue = maxOf { it.value }
        val minGraph = minValue.toFloat() - 20
        val maxGraph = maxValue.toFloat() + 20

        ChartLayout(modifier = Modifier.fillMaxSize()) {
            XYGraph(
                xAxisModel = CategoryAxisModel(hourList),
                yAxisModel = FloatLinearAxisModel(range = minGraph..maxGraph),
            ) {
                val points = mapIndexed { index, value ->
                    if (value.value == minValue) {
                        hourMin = hourList[index]
                    }
                    if (value.value == maxValue) {
                        hourMax = hourList[index]
                    }
                    Logger.d("JAE ${hourList[index]} - $value")
                    DefaultPoint(hourList[index], value.value.toFloat())
                }
                Column {
                    Text("Minimun prize: $minValue € at $hourMin")
                    Text("Maximum prize: $maxValue € at $hourMax")
                    chart(
                        points,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

private val hourList: List<String> =
    List(25) { it.toString() }

@Composable
private fun XYGraphScope<String, Float>.chart(
    data: List<DefaultPoint<String, Float>>,
    modifier: Modifier = Modifier,
) {
    LinePlot(
        data = data,
        modifier = modifier.fillMaxSize(),
        lineStyle = LineStyle(
            brush = SolidColor(Color.Black),
            strokeWidth = 2.dp,
            pathEffect = PathEffect.cornerPathEffect(2f),
        ),
    )
}

@Composable
private fun TopAppBar(
    sendEvent: (event: Event) -> Unit,
    state: State,
) {
    TopAppBar(
        title = { Text("Electricity prize") },
        navigationIcon =
            {
                IconButton(
                    onClick = { sendEvent(Event.OnUpButtonClicked) },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            },
        actions = {
            IconButton(
                onClick = { sendEvent(Event.OnSwipeToRefresh) },
                enabled = !state.loading,
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                )
            }
        },
    )
}
