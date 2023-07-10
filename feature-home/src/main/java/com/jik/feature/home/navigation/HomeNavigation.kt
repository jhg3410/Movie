package com.jik.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jik.feature.home.HomeScreen

object HomeNavigation {
    const val route = "home"


    fun NavController.navigateHome(navOptions: NavOptions? = null) {
        navigate(route, navOptions)
    }

    fun NavGraphBuilder.installHomeScreen() {
        composable(
            route = HomeNavigation.route
        ) {
            HomeScreen()
        }
    }
}