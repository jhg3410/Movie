package com.jik.feature.home.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jik.core.designsystem.component.NavigationBarCornerSize
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
            Spacer(modifier = Modifier.padding(bottom = NavigationBarCornerSize))
        }
    }
}