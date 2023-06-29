package com.jik.feature.popular.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jik.feature.detail.navigation.DetailNavigation.navigateDetail
import com.jik.feature.popular.PopularScreen


object PopularNavigation {
    const val route = "popular"

    fun NavController.navigatePopular() {
        navigate(route)
    }

    fun NavGraphBuilder.installPopularScreen(
        navController: NavController,
    ) {
        composable(
            route = PopularNavigation.route
        ) {
            PopularScreen(
                onPosterCardClick = { movieId -> navController.navigateDetail(movieId) }
            )
        }
    }
}