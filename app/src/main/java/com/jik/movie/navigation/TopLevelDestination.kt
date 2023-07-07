package com.jik.movie.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.jik.core.designsystem.icon.MovieIcons
import com.jik.core.ui.R
import com.jik.feature.home.navigation.HomeNavigation
import com.jik.feature.popular.navigation.PopularNavigation
import com.jik.feature.home.R as homeR
import com.jik.feature.popular.R as popularR


enum class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    HOME(
        route = HomeNavigation.route,
        selectedIcon = MovieIcons.HomeRounded,
        unselectedIcon = MovieIcons.HomeRounded,
        iconTextId = homeR.string.home,
        titleTextId = R.string.app_name
    ),
    POPULAR(
        route = PopularNavigation.route,
        selectedIcon = MovieIcons.LocalFireDepartmentRounded,
        unselectedIcon = MovieIcons.LocalFireDepartmentRounded,
        iconTextId = popularR.string.popular,
        titleTextId = popularR.string.popular
    )
}