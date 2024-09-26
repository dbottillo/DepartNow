package com.dbottillo.departnow.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeRoute(
    navigateToDepartures: () -> Unit
) {
    HomeScreen(
        navigateToDepartures = navigateToDepartures
    )
}

@Composable
fun HomeScreen(navigateToDepartures: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 16.dp)
    ) {
        OutlinedButton(
            modifier = Modifier.padding(start = 16.dp),
            onClick = { navigateToDepartures.invoke() }
        ) {
            Text("Open Train & Bus Departures")
        }
    }
}
