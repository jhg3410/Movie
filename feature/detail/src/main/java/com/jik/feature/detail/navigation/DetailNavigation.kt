package com.jik.feature.detail.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jik.core.ui.util.StatusBarColor
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

    fun NavGraphBuilder.installDetailScreen(
        enterTransition: EnterTransition,
        exitTransition: ExitTransition,
        navigateUp: () -> Unit
    ) {
        composable(
            route = routeWithArgs,
            arguments = arguments,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition }
        ) {
            StatusBarColor(color = Color.Transparent)
            DetailScreen(
                modifier = Modifier.fillMaxSize(),
                navigateUp = navigateUp
            )
        }
    }
}

internal class MovieArgs(val movieId: Long) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        movieId = checkNotNull(savedStateHandle[DetailNavigation.movieIdArg])
    )
}