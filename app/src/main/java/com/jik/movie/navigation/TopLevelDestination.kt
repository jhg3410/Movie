package com.jik.movie.navigation

import com.jik.feature.popular.navigation.PopularNavigation

enum class TopLevelDestination(
    val route: String
) {
    POPULAR(PopularNavigation.route)
}
