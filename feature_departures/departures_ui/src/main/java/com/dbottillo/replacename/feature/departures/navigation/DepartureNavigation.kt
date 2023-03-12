package com.dbottillo.replacename.feature.departures.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dbottillo.replacename.feature.departures.ui.DeparturesRoute

const val DEPARTURES_NAVIGATION_ROUTE = "departures"

fun NavController.navigateToDepartures(navOptions: NavOptions? = null) {
    this.navigate(DEPARTURES_NAVIGATION_ROUTE, navOptions)
}

fun NavGraphBuilder.departuresScreen(onBackClick: () -> Unit) {
    composable(route = DEPARTURES_NAVIGATION_ROUTE) {
        DeparturesRoute(onBackClick = onBackClick)
    }
}
