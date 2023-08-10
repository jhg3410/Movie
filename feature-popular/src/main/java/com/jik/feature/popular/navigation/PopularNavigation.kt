package com.jik.feature.popular.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jik.feature.popular.PopularScreen


object PopularNavigation {
    const val route = "popular"

    fun NavController.navigatePopular(navOptions: NavOptions? = null) {
        navigate(route, navOptions)
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.installPopularScreen(
        onPosterClick: (Long) -> Unit
    ) {
        composable(
            route = PopularNavigation.route
        ) {
            PopularScreen(onPosterClick = onPosterClick)
        }
    }
}