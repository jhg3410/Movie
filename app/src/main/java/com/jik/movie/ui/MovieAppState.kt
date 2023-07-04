package com.jik.movie.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.jik.feature.home.navigation.HomeNavigation.navigateHome
import com.jik.feature.popular.navigation.PopularNavigation.navigatePopular
import com.jik.movie.navigation.TopLevelDestination

@Composable
fun rememberMovieAppState(
    navController: NavHostController = rememberNavController(),
): MovieAppState = remember(navController) {
    MovieAppState(
        navController = navController
    )
}


@Stable
class MovieAppState(
    val navController: NavHostController,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    private val topLevelDestinationRoutes = TopLevelDestination.values().map { it.route }

    @OptIn(ExperimentalMaterial3Api::class)
    val topAppBarScrollBehavior
        @Composable get() = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isTopLevelDestination: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in topLevelDestinationRoutes

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().toList()

    fun navigateToDestination(topLevelDestination: TopLevelDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateHome(navOptions)
            TopLevelDestination.POPULAR -> navController.navigatePopular(navOptions)
        }
    }
}