package com.jik.movie.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jik.core.designsystem.component.MovieNavigationBar
import com.jik.core.designsystem.component.MovieNavigationBarItem
import com.jik.core.designsystem.component.NavigationBarCornerSize
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.movie.navigation.MovieNavHost
import com.jik.movie.navigation.TopLevelDestination

@Composable
fun MovieApp() {
    MovieTheme(
        dynamicColor = false
    ) {
        val appState = rememberMovieAppState()
        val destination = appState.currentTopLevelDestination

        Scaffold(
            bottomBar = {
                if (destination != null) {
                    MovieBottomBar(
                        topLevelDestination = appState.topLevelDestinations,
                        currentDestination = destination,
                        onNavigateToDestination = appState::navigateToDestination,
                    )
                }
            }
        ) {
            val topPadding = it.calculateTopPadding()
            val bottomPadding = it.calculateBottomPadding()

            MovieNavHost(
                navController = appState.navController,
                modifier = Modifier.padding(
                    top = topPadding,
                    bottom = if (destination != null && bottomPadding > 0.dp) bottomPadding - NavigationBarCornerSize
                    else bottomPadding
                )
            )
        }
    }
}

@Composable
fun MovieBottomBar(
    topLevelDestination: List<TopLevelDestination>,
    currentDestination: TopLevelDestination,
    modifier: Modifier = Modifier,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    MovieNavigationBar(
        modifier = modifier,
        content = {
            topLevelDestination.forEach { destination ->
                val selected = destination.route == currentDestination.route
                MovieNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    iconImageVector = destination.selectedIcon,
                    selectedIconImageVector = destination.selectedIcon,
                    labelTextId = destination.iconTextId,
                )
            }
        }
    )
}