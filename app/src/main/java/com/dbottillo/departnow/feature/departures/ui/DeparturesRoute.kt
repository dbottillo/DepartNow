package com.dbottillo.departnow.feature.departures.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dbottillo.departnow.designsystem.DepartNowAppTheme
import com.dbottillo.departnow.feature.departures.DeparturesUi
import com.dbottillo.departnow.feature.departures.DeparturesUiData
import com.dbottillo.departnow.feature.departures.DeparturesUiState
import com.dbottillo.departnow.feature.departures.DeparturesUiStatus
import com.dbottillo.departnow.feature.departures.DeparturesViewModel

@Composable
fun DeparturesRoute(
    viewModel: DeparturesViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    DeparturesScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onRefresh = viewModel::onRefresh
    )
}

@Composable
fun DeparturesScreen(
    uiState: DeparturesUiState,
    onBackClick: () -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable {
                onRefresh()
            }
    ) {
        Row {
            IconButton(onClick = { onBackClick.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(top = 16.dp, end = 16.dp),
                text = "Last updated ${uiState.departureData.lastTimeUpdated}",
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Black.copy(0.8f))
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                /*TrainDeparture(uiState.departureData.firstTrain)
                TrainDeparture(uiState.departureData.secondTrain)*/
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (uiState.departureData.trainBefore.isNotEmpty()) {
                        Text(text = uiState.departureData.trainBefore, color = Color.White)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Departure(uiState.departureData.firstTrain)
                        Departure(uiState.departureData.secondTrain)
                    }
                    Departures(uiState.departureData.otherTrains)
                }
                HorizontalDivider(Modifier.padding(all = 16.dp))
                /*BusDeparture(uiState.departureData.firstBus)
                BusDeparture(uiState.departureData.secondBus)*/
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    uiState.departureData.beforeBus?.let {
                        Text(text = it, color = Color.White,)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Departure(uiState.departureData.firstBus)
                        Departure(uiState.departureData.secondBus)
                    }
                    Departures(uiState.departureData.otherBus)
                }
            }

            if (uiState.status is DeparturesUiStatus.Loading) {
                CircularProgressIndicator(
                    color = Color.Cyan,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
            if (uiState.status is DeparturesUiStatus.Error) {
                Text(
                    text = "error: ${uiState.status.throwable}",
                    color = Color.Red,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
fun Departure(departure: DeparturesUi) {
   /* Row {
        when (trainDeparture) {
            is DeparturesUiTrain.Data -> {
                Text(trainDeparture.minutes.toString(), color = Color.White, fontSize = 130.sp)
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text("${trainDeparture.time} to", color = Color.White, fontSize = 30.sp)
                    Text(trainDeparture.destination, color = Color.White, fontSize = 26.sp)
                }
            }
            DeparturesUiTrain.None -> {
                Text("No train is departing", color = Color.White, fontSize = 30.sp)
            }
        }
    }*/
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (departure) {
            is DeparturesUi.Data -> {
                Text(
                    departure.minutes.toString(),
                    color = Color.White,
                    fontSize = 140.sp
                )
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Text("${departure.time} to", color = Color.White, fontSize = 22.sp)
                    Text(departure.destination, color = Color.White, fontSize = 20.sp)
                }
            }
            DeparturesUi.None -> {
                Text(
                    "No\ndeparture",
                    color = Color.White,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun Departures(other: List<DeparturesUi>) {
    if (other.isNotEmpty()) {
        val text = other.filterIsInstance<DeparturesUi.Data>().joinToString(separator = " - ") { train ->
            "${train.minutes} (${train.time})"
        }
        Text(
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = text,
            maxLines = 1,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeparturesScreenDataPreview() {
    DepartNowAppTheme(darkTheme = false) {
        DeparturesScreen(
            DeparturesUiState(
            departureData = DeparturesUiData(
                firstTrain = DeparturesUi.Data(
                    minutes = 15,
                    destination = "Moorgate",
                    time = "15:16"
                ),
                secondTrain = DeparturesUi.Data(
                    minutes = 20,
                    destination = "Moorgate",
                    time = "15:41"
                ),
                otherTrains = listOf(
                    DeparturesUi.Data(
                        minutes = 35,
                        destination = "Moorgate",
                        time = "15:51"
                    ),
                    DeparturesUi.Data(
                        minutes = 50,
                        destination = "Moorgate",
                        time = "16:31"
                    )
                ),
                beforeBus = "In 1 (12:00)",
                firstBus = DeparturesUi.Data(
                    minutes = 2,
                    time = "15:16",
                    destination = "Finsbury Park"
                ),
                secondBus = DeparturesUi.Data(
                    minutes = 4,
                    time = "15:16",
                    destination = "Finsbury Park"
                ),
                otherBus = listOf(
                    DeparturesUi.Data(
                        minutes = 12,
                        time = "13:16",
                        destination = "Finsbury Park"
                    ),
                    DeparturesUi.Data(
                        minutes = 16,
                        time = "16:16",
                        destination = "Finsbury Park"
                    ),
                )
            ),
            status = DeparturesUiStatus.Idle
        ),
            onBackClick = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeparturesScreenErrorPreview() {
    DepartNowAppTheme(darkTheme = false) {
        DeparturesScreen(
            DeparturesUiState(
            departureData = DeparturesUiData(
                trainBefore = "In 20 (12:00) - In 5 (10:00)",
                firstTrain = DeparturesUi.Data(
                    minutes = 15,
                    destination = "Moorgate",
                    time = "15:16"
                ),
                secondTrain = DeparturesUi.Data(
                    minutes = 30,
                    destination = "Moorgate",
                    time = "15:41"
                ),
                otherTrains = emptyList(),
                firstBus = DeparturesUi.Data(
                    minutes = 5,
                    time = "15:16",
                    destination = "Moorgate"
                ),
                secondBus = DeparturesUi.Data(
                    minutes = 6,
                    time = "15:16",
                    destination = "Moorgate"
                ),
                otherBus = emptyList()
            ),
            status = DeparturesUiStatus.Error(Throwable("error"))
        ),
            onBackClick = {},
            onRefresh = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DeparturesScreenEmptyPreview() {
    DepartNowAppTheme(darkTheme = false) {
        DeparturesScreen(
            DeparturesUiState(
                departureData = DeparturesUiData(
                    trainBefore = "",
                    firstTrain = DeparturesUi.None,
                    secondTrain = DeparturesUi.None,
                    otherTrains = emptyList(),
                    firstBus = DeparturesUi.None,
                    secondBus = DeparturesUi.None,
                    otherBus = emptyList()
                ),
                status = DeparturesUiStatus.Error(Throwable("error"))
            ),
            onBackClick = {},
            onRefresh = {}
        )
    }
}
