package com.dbottillo.replacename.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dbottillo.replacename.feature.home.ui.HomeRoute

const val HOME_NAVIGATION_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_NAVIGATION_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen(navigateToDepartures: () -> Unit) {
    composable(route = HOME_NAVIGATION_ROUTE) {
        HomeRoute(
            navigateToDepartures = navigateToDepartures
        )
    }
}
