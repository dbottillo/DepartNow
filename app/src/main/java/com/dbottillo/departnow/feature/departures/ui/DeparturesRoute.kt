package com.dbottillo.departnow.feature.departures.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dbottillo.departnow.designsystem.DepartNowAppTheme
import com.dbottillo.departnow.feature.departures.DeparturesUiBus
import com.dbottillo.departnow.feature.departures.DeparturesUiData
import com.dbottillo.departnow.feature.departures.DeparturesUiState
import com.dbottillo.departnow.feature.departures.DeparturesUiStatus
import com.dbottillo.departnow.feature.departures.DeparturesUiTrain
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
            Column {
                TrainDeparture(uiState.departureData.firstTrain)
                TrainDeparture(uiState.departureData.secondTrain)
                TrainDepartures(uiState.departureData.thirdTrain, uiState.departureData.fourthTrain)
                HorizontalDivider(Modifier.padding(all = 16.dp))
                uiState.departureData.beforeBus?.let {
                    Text(text = it)
                }
                BusDeparture(uiState.departureData.firstBus)
                BusDeparture(uiState.departureData.secondBus)
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
fun TrainDeparture(trainDeparture: DeparturesUiTrain) {
    Row {
        when (trainDeparture) {
            is DeparturesUiTrain.Data -> {
                Text(trainDeparture.minutes.toString(), color = Color.White, fontSize = 140.sp)
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
    }
}

@Composable
fun TrainDepartures(trainDepartureThird: DeparturesUiTrain, trainDepartureFourth: DeparturesUiTrain) {
    val text = buildString {
        append("Also ")
        when (trainDepartureThird) {
            is DeparturesUiTrain.Data -> append("in ${trainDepartureThird.minutes} (${trainDepartureThird.time})")
            DeparturesUiTrain.None -> append("no train is departing")
        }
        append(" - ")
        when (trainDepartureFourth) {
            is DeparturesUiTrain.Data -> append("In ${trainDepartureFourth.minutes} (${trainDepartureFourth.time})")
            DeparturesUiTrain.None -> append("No train is departing")
        }
    }
    Text(
        text = text
    )
}

@Composable
fun BusDeparture(busDeparture: DeparturesUiBus) {
    Row {
        when (busDeparture) {
            is DeparturesUiBus.Data -> {
                Text(busDeparture.minutes.toString(), color = Color.White, fontSize = 140.sp)
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text("${busDeparture.time} to", color = Color.White, fontSize = 30.sp)
                    Text(busDeparture.destination, color = Color.White, fontSize = 26.sp)
                }
            }
            DeparturesUiBus.None -> {
                Text("No bus is departing", color = Color.White, fontSize = 30.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeparturesScreenDataPreview() {
    DepartNowAppTheme(darkTheme = false) {
        DeparturesScreen(
            DeparturesUiState(
            departureData = DeparturesUiData(
                firstTrain = DeparturesUiTrain.Data(
                    minutes = 15,
                    destination = "Moorgate",
                    time = "15:16"
                ),
                secondTrain = DeparturesUiTrain.Data(
                    minutes = 20,
                    destination = "Moorgate",
                    time = "15:41"
                ),
                thirdTrain = DeparturesUiTrain.Data(
                    minutes = 35,
                    destination = "Moorgate",
                    time = "15:51"
                ),
                fourthTrain = DeparturesUiTrain.Data(
                    minutes = 50,
                    destination = "Moorgate",
                    time = "16:31"
                ),
                firstBus = DeparturesUiBus.Data(
                    minutes = 2,
                    time = "15:16",
                    destination = "Moorgate"
                ),
                secondBus = DeparturesUiBus.Data(
                    minutes = 4,
                    time = "15:16",
                    destination = "Moorgate"
                ),
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
                firstTrain = DeparturesUiTrain.Data(
                    minutes = 15,
                    destination = "Moorgate",
                    time = "15:16"
                ),
                secondTrain = DeparturesUiTrain.Data(
                    minutes = 30,
                    destination = "Moorgate",
                    time = "15:41"
                ),
                thirdTrain = DeparturesUiTrain.None,
                fourthTrain = DeparturesUiTrain.None,
                firstBus = DeparturesUiBus.Data(
                    minutes = 5,
                    time = "15:16",
                    destination = "Moorgate"
                ),
                secondBus = DeparturesUiBus.Data(
                    minutes = 6,
                    time = "15:16",
                    destination = "Moorgate"
                ),
            ),
            status = DeparturesUiStatus.Error(Throwable("error"))
        ),
            onBackClick = {},
            onRefresh = {}
        )
    }
}
