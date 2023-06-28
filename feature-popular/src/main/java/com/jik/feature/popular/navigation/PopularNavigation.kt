package com.jik.feature.popular.navigation

import androidx.navigation.NavController


object PopularNavigation {
    const val route = "popular"

    fun NavController.navigatePopular() {
        navigate(route)
    }
}