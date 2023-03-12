package com.dbottillo.replacename.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.dbottillo.replacename.feature.home.HomeViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dbottillo.replacename.Lce
import com.dbottillo.replacename.Todo

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDepartures: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState,
        navigateToDepartures = navigateToDepartures
    )
}

@Composable
fun HomeScreen(uiState: Lce<Todo>, navigateToDepartures: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            when (uiState) {
                is Lce.Data -> {
                    Text("Result: ${uiState.data}")
                }
                is Lce.Error -> {
                    Text("Error: ${uiState.throwable}")
                }
                Lce.Loading -> {
                    Text("Loading")
                }
            }
        }
        OutlinedButton(
            modifier = Modifier.padding(start = 16.dp),
            onClick = { navigateToDepartures.invoke() }
        ) {
            Text("Open Departures")
        }
    }
}
