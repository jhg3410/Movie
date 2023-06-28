package com.jik.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

object DetailNavigation {

    private const val route = "detail"
    const val movieIdArg = "movieId"
    const val routeWithArgs = "${route}/{${movieIdArg}}"

    val arguments = listOf(
        navArgument(movieIdArg) {
            type = NavType.LongType
        }
    )

    fun NavController.navigateDetail(movieId: Long) {
        navigate("${route}/$movieId")
    }
}



