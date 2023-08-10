package com.jik.movie.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jik.core.designsystem.component.MovieNavigationBar
import com.jik.core.designsystem.component.MovieNavigationBarItem
import com.jik.core.designsystem.component.NavigationBarCornerSize
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.core.ui.util.modifier.conditional
import com.jik.movie.navigation.MovieNavHost
import com.jik.movie.navigation.TopLevelDestination

@Composable
fun MovieApp() {
    MovieTheme {
        val appState = rememberMovieAppState()
        val destination = appState.currentTopLevelDestination

        Scaffold(
            bottomBar = {
                MovieBottomBar(
                    visible = destination != null,
                    topLevelDestination = appState.topLevelDestinations,
                    currentDestination = destination,
                    onNavigateToDestination = appState::navigateToDestination,
                )
            },
            contentWindowInsets = WindowInsets(0.dp)
        ) {
            val topPadding = it.calculateTopPadding()
            val bottomPadding = it.calculateBottomPadding()

            MovieNavHost(
                navController = appState.navController,
                modifier = Modifier.conditional(destination != null) {
                    padding(
                        top = topPadding,
                        bottom = if (destination != null && bottomPadding > 0.dp) bottomPadding - NavigationBarCornerSize
                        else bottomPadding
                    )
                }
            )
        }
    }
}

@Composable
fun MovieBottomBar(
    visible: Boolean,
    topLevelDestination: List<TopLevelDestination>,
    currentDestination: TopLevelDestination?,
    modifier: Modifier = Modifier,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        MovieNavigationBar(
            modifier = modifier,
            content = {
                topLevelDestination.forEach { destination ->
                    val selected = destination.route == currentDestination?.route
                    MovieNavigationBarItem(
                        selected = selected,
                        onClick = { onNavigateToDestination(destination) },
                        iconImageVector = destination.icon,
                        selectedIconImageVector = destination.icon,
                        labelTextId = destination.iconTextId,
                    )
                }
            }
        )
    }
}