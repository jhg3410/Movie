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
import com.jik.feature.home.navigation.HomeNavigation
import com.jik.feature.home.navigation.HomeNavigation.navigateHome
import com.jik.feature.popular.navigation.PopularNavigation
import com.jik.feature.popular.navigation.PopularNavigation.navigatePopular
import com.jik.movie.navigation.TopLevelDestination
import com.jik.movie.navigation.TopLevelDestination.HOME
import com.jik.movie.navigation.TopLevelDestination.POPULAR

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

    @OptIn(ExperimentalMaterial3Api::class)
    val topAppBarScrollBehavior
        @Composable get() = TopAppBarDefaults.enterAlwaysScrollBehavior()

    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HomeNavigation.route -> HOME
            PopularNavigation.route -> POPULAR
            else -> null
        }

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
            HOME -> navController.navigateHome(navOptions)
            POPULAR -> navController.navigatePopular(navOptions)
        }
    }
}