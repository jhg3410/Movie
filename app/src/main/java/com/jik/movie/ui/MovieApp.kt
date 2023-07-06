package com.jik.movie.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.jik.core.designsystem.component.*
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.movie.navigation.MovieNavHost
import com.jik.movie.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    MovieTheme(
        dynamicColor = false
    ) {
        val appState = rememberMovieAppState()
        val scrollBehavior = appState.topAppBarScrollBehavior
        val destination = appState.currentTopLevelDestination

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                if (destination != null) {
                    MovieTopAppBar(
                        titleRes = destination.titleTextId,
                        scrollBehavior = scrollBehavior
                    )
                }
            },
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
                ),
                ExpandTopBar = {
                    if (scrollBehavior.state.heightOffset < 0.0) {
                        scrollBehavior.state.heightOffset = 0f
                    }
                }
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