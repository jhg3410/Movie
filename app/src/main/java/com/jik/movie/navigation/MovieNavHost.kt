package com.jik.movie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jik.feature.detail.DetailScreen
import com.jik.feature.detail.navigation.DetailNavigation
import com.jik.feature.detail.navigation.DetailNavigation.navigateDetail
import com.jik.feature.popular.PopularScreen
import com.jik.feature.popular.navigation.PopularNavigation

@Composable
fun MovieNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = PopularNavigation.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = PopularNavigation.route
        ) {
            PopularScreen(
                onPosterCardClick = { movieId -> navController.navigateDetail(movieId) }
            )
        }
        composable(
            route = DetailNavigation.routeWithArgs,
            arguments = DetailNavigation.arguments
        ) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getLong(DetailNavigation.movieIdArg)
                ?: throw IllegalStateException("movieId not found")
            DetailScreen(movieId = movieId)
        }
    }
}