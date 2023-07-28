package com.jik.movie.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.jik.core.designsystem.icon.MovieIcons
import com.jik.feature.home.navigation.HomeNavigation
import com.jik.feature.popular.navigation.PopularNavigation
import com.jik.feature.home.R as homeR
import com.jik.feature.popular.R as popularR


enum class TopLevelDestination(
    val route: String,
    val icon: ImageVector,
    val iconTextId: Int,
) {
    HOME(
        route = HomeNavigation.route,
        icon = MovieIcons.HomeRounded,
        iconTextId = homeR.string.home,
    ),
    POPULAR(
        route = PopularNavigation.route,
        icon = MovieIcons.LocalFireDepartmentRounded,
        iconTextId = popularR.string.popular,
    )
}