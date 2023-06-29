package com.jik.movie.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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

    private val topLevelDestinationRoutes = TopLevelDestination.values().map { it.route }

    @OptIn(ExperimentalMaterial3Api::class)
    val topAppBarScrollBehavior
        @Composable get() = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isTopLevelDestination: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in topLevelDestinationRoutes
}