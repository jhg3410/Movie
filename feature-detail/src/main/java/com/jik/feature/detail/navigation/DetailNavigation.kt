package com.jik.feature.detail.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jik.feature.detail.DetailScreen

object DetailNavigation {

    const val movieIdArg = "movieId"
    private const val route = "detail"
    private const val routeWithArgs = "${route}/{${movieIdArg}}"

    private val arguments = listOf(
        navArgument(movieIdArg) {
            type = NavType.LongType
        }
    )

    fun NavController.navigateDetail(movieId: Long) {
        navigate("${route}/$movieId")
    }

    fun NavGraphBuilder.installDetailScreen(modifier: Modifier = Modifier) {
        composable(
            route = routeWithArgs,
            arguments = arguments
        ) {
            DetailScreen(modifier = modifier)
        }
    }
}

internal class MovieArgs(val movieId: Long) {
    constructor(savedStateHandle: SavedStateHandle): this(
        movieId = checkNotNull(savedStateHandle[DetailNavigation.movieIdArg])
    )
}