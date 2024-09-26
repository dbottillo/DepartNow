package com.dbottillo.departnow.navigation

import com.dbottillo.departnow.R
import com.dbottillo.departnow.designsystem.AppIcons
import com.dbottillo.departnow.designsystem.Icon

enum class TopLevelDestination(
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    val iconTextId: Int,
    val titleTextId: Int
) {
    HOME(
        selectedIcon = Icon.DrawableResourceIcon(AppIcons.Home),
        unselectedIcon = Icon.DrawableResourceIcon(AppIcons.Home),
        iconTextId = R.string.tab_home,
        titleTextId = R.string.tab_home
    ),
    ABOUT(
        selectedIcon = Icon.DrawableResourceIcon(AppIcons.About),
        unselectedIcon = Icon.DrawableResourceIcon(AppIcons.About),
        iconTextId = R.string.tab_about,
        titleTextId = R.string.tab_about
    )
}
