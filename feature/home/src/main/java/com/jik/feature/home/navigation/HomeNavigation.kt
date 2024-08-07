package com.jik.feature.home.navigation

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jik.core.ui.util.StatusBarColor
import com.jik.feature.home.HomeScreen

object HomeNavigation {
    const val route = "home"


    fun NavController.navigateHome(navOptions: NavOptions? = null) {
        navigate(route, navOptions)
    }

    fun NavGraphBuilder.installHomeScreen(
        onPosterClick: (Long) -> Unit
    ) {
        composable(
            route = HomeNavigation.route
        ) {
            StatusBarColor(color = Color.Transparent)
            HomeScreen(onPosterClick = onPosterClick)
        }
    }
}