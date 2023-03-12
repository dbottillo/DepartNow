package com.dbottillo.replacename.feature.departures.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.dbottillo.replacename.Lce
import com.dbottillo.replacename.feature.departures.DeparturesData
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
fun DeparturesScreen(uiState: Lce<DeparturesData>, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        IconButton(onClick = { onBackClick.invoke() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f)
            )
        }
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
}
