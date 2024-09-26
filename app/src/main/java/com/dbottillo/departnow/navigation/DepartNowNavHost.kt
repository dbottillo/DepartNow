package com.dbottillo.departnow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dbottillo.departnow.feature.about.navigation.aboutScreen
import com.dbottillo.departnow.feature.departures.navigation.departuresScreen
import com.dbottillo.departnow.feature.departures.navigation.navigateToDepartures
import com.dbottillo.departnow.feature.home.navigation.HOME_NAVIGATION_ROUTE
import com.dbottillo.departnow.feature.home.navigation.homeScreen

@Composable
fun DepartNowNavHost(
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
