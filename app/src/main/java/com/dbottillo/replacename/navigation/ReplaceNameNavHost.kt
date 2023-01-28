package com.dbottillo.replacename.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dbottillo.replacename.feature.about.navigation.aboutScreen
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
        homeScreen()
        aboutScreen()
       /* interestsGraph(
            navigateToTopic = { topicId ->
                navController.navigateToTopic(topicId)
            },
            nestedGraphs = {
                topicScreen(onBackClick)
            },
        )*/
    }
}
