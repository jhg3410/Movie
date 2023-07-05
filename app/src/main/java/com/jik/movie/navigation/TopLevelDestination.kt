package com.jik.movie.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import com.jik.core.designsystem.icon.MovieIcons
import com.jik.feature.home.navigation.HomeNavigation
import com.jik.feature.popular.navigation.PopularNavigation
import com.jik.feature.home.R as homeR
import com.jik.feature.popular.R as popularR


enum class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
) {
    HOME(
        route = HomeNavigation.route,
        selectedIcon = MovieIcons.HomeRounded,
        unselectedIcon = MovieIcons.HomeRounded,
        iconTextId = homeR.string.home
    ),
    POPULAR(
        route = PopularNavigation.route,
        selectedIcon = MovieIcons.LocalFireDepartmentRounded,
        unselectedIcon = MovieIcons.LocalFireDepartmentRounded,
        iconTextId = popularR.string.popular
    )
}

fun TopLevelDestination.isCurrentDestination(navDestination: NavDestination?): Boolean {
    return this.route == (navDestination?.route ?: false)
}