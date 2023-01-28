package com.dbottillo.replacename.feature.home.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.dbottillo.replacename.feature.home.HomeViewModel
import androidx.compose.runtime.getValue
import com.dbottillo.replacename.Lce
import com.dbottillo.replacename.Todo

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState
    )
}

@Composable
fun HomeScreen(uiState: Lce<Todo>) {
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
