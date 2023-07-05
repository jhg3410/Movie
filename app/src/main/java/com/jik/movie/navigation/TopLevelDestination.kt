package com.jik.movie.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import com.jik.core.designsystem.icon.MovieIcons
import com.jik.feature.home.navigation.HomeNavigation
import com.jik.feature.popular.navigation.PopularNavigation

enum class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    HOME(
        route = HomeNavigation.route,
        selectedIcon = MovieIcons.Home,
        unselectedIcon = MovieIcons.HomeOutlined
    ),
    POPULAR(
        route = PopularNavigation.route,
        selectedIcon = MovieIcons.LocalFireDepartment,
        unselectedIcon = MovieIcons.LocalFireDepartmentOutlined
    )
}

fun TopLevelDestination.isCurrentDestination(navDestination: NavDestination?): Boolean {
    return this.route == (navDestination?.route ?: false)
}