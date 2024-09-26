package com.dbottillo.departnow.feature.departures.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
                Divider(modifier = Modifier.height(1.dp).padding(all = 16.dp))
                TrainDeparture(uiState.departureData.thirdTrain)
                TrainDeparture(uiState.departureData.fourthTrain)
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

@Suppress("unused")
@Composable
fun ColumnScope.TrainDeparture(trainDeparture: DeparturesUiTrain) {
    Row {
        when (trainDeparture) {
            is DeparturesUiTrain.Data -> {
                Text(trainDeparture.minutes, color = Color.White, fontSize = 140.sp)
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

@Suppress("unused")
@Composable
fun ColumnScope.BusDeparture(busDeparture: DeparturesUiBus) {
    Row {
        when (busDeparture) {
            is DeparturesUiBus.Data -> {
                Text(busDeparture.minutes, color = Color.White, fontSize = 140.sp)
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
                    minutes = "15",
                    destination = "Moorgate",
                    time = "15:16"
                ),
                secondTrain = DeparturesUiTrain.Data(
                    minutes = "20",
                    destination = "Moorgate",
                    time = "15:41"
                ),
                thirdTrain = DeparturesUiTrain.Data(
                    minutes = "35",
                    destination = "Moorgate",
                    time = "15:51"
                ),
                fourthTrain = DeparturesUiTrain.Data(
                    minutes = "50",
                    destination = "Moorgate",
                    time = "16:31"
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
                firstTrain = DeparturesUiTrain.Data(
                    minutes = "15",
                    destination = "Moorgate",
                    time = "15:16"
                ),
                secondTrain = DeparturesUiTrain.Data(
                    minutes = "30",
                    destination = "Moorgate",
                    time = "15:41"
                ),
                thirdTrain = DeparturesUiTrain.None,
                fourthTrain = DeparturesUiTrain.None
            ),
            status = DeparturesUiStatus.Error(Throwable("error"))
        ),
            onBackClick = {},
            onRefresh = {}
        )
    }
}
