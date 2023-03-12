package com.dbottillo.replacename.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dbottillo.replacename.feature.about.navigation.aboutScreen
import com.dbottillo.replacename.feature.departures.navigation.departuresScreen
import com.dbottillo.replacename.feature.departures.navigation.navigateToDepartures
import com.dbottillo.replacename.feature.home.navigation.HOME_NAVIGATION_ROUTE
import com.dbottillo.replacename.feature.home.navigation.homeScreen

@Composable
fun ReplaceNameNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_NAVIGATION_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen {
            navController.navigateToDepartures()
        }
        aboutScreen()
        departuresScreen(onBackClick = onBackClick)
    }
}
