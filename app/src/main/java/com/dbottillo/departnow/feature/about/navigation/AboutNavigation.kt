package com.dbottillo.departnow.feature.about.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dbottillo.departnow.feature.about.ui.AboutRoute

const val ABOUT_NAVIGATION_ROUTE = "about"

fun NavController.navigateToAbout(navOptions: NavOptions? = null) {
    this.navigate(ABOUT_NAVIGATION_ROUTE, navOptions)
}

fun NavGraphBuilder.aboutScreen() {
    composable(route = ABOUT_NAVIGATION_ROUTE) {
        AboutRoute()
    }
}
