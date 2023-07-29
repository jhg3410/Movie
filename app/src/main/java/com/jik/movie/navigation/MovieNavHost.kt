package com.jik.movie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jik.feature.detail.navigation.DetailNavigation.installDetailScreen
import com.jik.feature.detail.navigation.DetailNavigation.navigateDetail
import com.jik.feature.home.navigation.HomeNavigation
import com.jik.feature.home.navigation.HomeNavigation.installHomeScreen
import com.jik.feature.popular.navigation.PopularNavigation.installPopularScreen

@Composable
fun MovieNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = HomeNavigation.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        installHomeScreen(
            onPosterClick = { movieId -> navController.navigateDetail(movieId) }
        )
        installPopularScreen(
            onPosterClick = { movieId -> navController.navigateDetail(movieId) }
        )
        installDetailScreen()
    }
}