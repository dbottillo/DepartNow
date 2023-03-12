package com.dbottillo.replacename.feature.departures.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
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
import com.dbottillo.replacename.designsystem.ReplaceNameAppTheme
import com.dbottillo.replacename.feature.departures.DeparturesUiData
import com.dbottillo.replacename.feature.departures.DeparturesUiState
import com.dbottillo.replacename.feature.departures.DeparturesUiStatus
import com.dbottillo.replacename.feature.departures.DeparturesUiTrainData
import com.dbottillo.replacename.feature.departures.DeparturesViewModel

@Composable
fun DeparturesRoute(
    viewModel: DeparturesViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    DeparturesScreen(
        uiState = uiState,
        onBackClick = onBackClick
    )
}

@Composable
fun DeparturesScreen(uiState: DeparturesUiState, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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
                .weight(0.5f)
                .background(Color.Black.copy(0.8f))
                .padding(16.dp)
        ) {
            Column {
                Train(uiState.departureData.firstTrain)
                Train(uiState.departureData.secondTrain)
            }

            if (uiState.status is DeparturesUiStatus.Loading) {
                CircularProgressIndicator(
                    color = Color.Cyan,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .background(Color.Black.copy(0.8f))
                .padding(16.dp)
        ) {
            if (uiState.status is DeparturesUiStatus.Error) {
                Text(
                    "error: ${uiState.status.throwable}",
                    color = Color.White
                )
            }
        }
    }
}

@Suppress("unused")
@Composable
fun ColumnScope.Train(train: DeparturesUiTrainData) {
    Row {
        Text(train.minutes.toString(), color = Color.White, fontSize = 140.sp)
        Column(
            modifier = Modifier
            .padding(start = 16.dp)
            .align(Alignment.CenterVertically)
        ) {
            Text("${train.time} to", color = Color.White, fontSize = 30.sp)
            Text(train.destination, color = Color.White, fontSize = 30.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeparturesScreenLoadingPreview() {
    ReplaceNameAppTheme(darkTheme = false) {
        DeparturesScreen(
            DeparturesUiState(
            departureData = DeparturesUiData(
                firstTrain = DeparturesUiTrainData(
                    minutes = 15,
                    destination = "Moorgate",
                    time = "15:16"
                ),
                secondTrain = DeparturesUiTrainData(
                    minutes = 30,
                    destination = "Moorgate",
                    time = "15:41"
                )
            ),
            status = DeparturesUiStatus.Loading
        )
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun DeparturesScreenErrorPreview() {
    ReplaceNameAppTheme(darkTheme = false) {
        DeparturesScreen(
            DeparturesUiState(
            departureData = DeparturesUiData(
                firstTrain = DeparturesUiTrainData(
                    minutes = 15,
                    destination = "Moorgate",
                    time = "15:16"
                ),
                secondTrain = DeparturesUiTrainData(
                    minutes = 30,
                    destination = "Moorgate",
                    time = "15:41"
                )
            ),
            status = DeparturesUiStatus.Error(Throwable("error"))
        )
        ) {}
    }
}
