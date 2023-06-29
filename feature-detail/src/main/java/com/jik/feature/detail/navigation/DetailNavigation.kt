package com.jik.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jik.feature.detail.DetailScreen

object DetailNavigation {

    private const val route = "detail"
    private const val movieIdArg = "movieId"
    private const val routeWithArgs = "${route}/{${movieIdArg}}"

    private val arguments = listOf(
        navArgument(movieIdArg) {
            type = NavType.LongType
        }
    )

    fun NavController.navigateDetail(movieId: Long) {
        navigate("${route}/$movieId")
    }

    fun NavGraphBuilder.installDetailScreen() {
        composable(
            route = routeWithArgs,
            arguments = arguments
        ) { navBackStackEntry ->
            val movieId =
                navBackStackEntry.arguments?.getLong(movieIdArg)
                    ?: throw IllegalStateException("movieId not found")
            DetailScreen(movieId = movieId)
        }
    }
}