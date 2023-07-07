package com.jik.feature.popular.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jik.core.designsystem.component.NavigationBarCornerSize
import com.jik.feature.detail.navigation.DetailNavigation.navigateDetail
import com.jik.feature.popular.PopularScreen


object PopularNavigation {
    const val route = "popular"

    fun NavController.navigatePopular(navOptions: NavOptions? = null) {
        navigate(route, navOptions)
    }

    fun NavGraphBuilder.installPopularScreen(
        navController: NavController,
    ) {
        composable(
            route = PopularNavigation.route
        ) {
            PopularScreen(
                onPosterCardClick = { movieId -> navController.navigateDetail(movieId) },
            )
            Spacer(modifier = Modifier.padding(bottom = NavigationBarCornerSize))
        }
    }
}