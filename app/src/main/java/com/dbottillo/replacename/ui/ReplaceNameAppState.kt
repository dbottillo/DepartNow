package com.dbottillo.replacename.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.dbottillo.replacename.feature.about.navigation.ABOUT_NAVIGATION_ROUTE
import com.dbottillo.replacename.feature.about.navigation.navigateToAbout
import com.dbottillo.replacename.feature.home.navigation.HOME_NAVIGATION_ROUTE
import com.dbottillo.replacename.feature.home.navigation.navigateToHome
import com.dbottillo.replacename.navigation.TopLevelDestination

@Composable
fun rememberReplaceNameAppState(
    navController: NavHostController = rememberNavController(),
): ReplaceNameAppState {
    NavigationTrackingSideEffect(navController)
    return remember(navController) {
        ReplaceNameAppState(navController)
    }
}

@Stable
class ReplaceNameAppState(
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HOME_NAVIGATION_ROUTE -> TopLevelDestination.HOME
            ABOUT_NAVIGATION_ROUTE -> TopLevelDestination.ABOUT
            else -> null
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.ABOUT -> navController.navigateToAbout(topLevelNavOptions)
        }
    }

    val shouldShowBottomBar: Boolean
        @Composable get() = currentTopLevelDestination != null

    fun onBackClick() {
        navController.popBackStack()
    }
}

@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
    val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        Log.i("TAG", "new destination: ${destination.route}")
    }
    navController.addOnDestinationChangedListener(listener)
}
