package com.jik.movie.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.jik.core.designsystem.component.*
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.core.ui.R
import com.jik.movie.navigation.MovieNavHost
import com.jik.movie.navigation.TopLevelDestination
import com.jik.movie.navigation.isCurrentDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    MovieTheme(
        dynamicColor = false
    ) {
        val appState = rememberMovieAppState()
        val scrollBehavior = appState.topAppBarScrollBehavior

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                if (appState.isTopLevelDestination) {
                    MovieTopAppBar(
                        titleRes = R.string.app_name,
                        scrollBehavior = scrollBehavior
                    )
                }
            },
            bottomBar = {
                if (appState.isTopLevelDestination) {
                    MovieBottomBar(
                        destination = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToDestination,
                        currentDestination = appState.currentDestination
                    )
                }
            }
        ) {
            val topPadding = it.calculateTopPadding()
            val bottomPadding = it.calculateBottomPadding()
            MovieNavHost(
                navController = appState.navController, modifier = Modifier.padding(
                    top = topPadding,
                    bottom = if (appState.isTopLevelDestination && bottomPadding > 0.dp) bottomPadding - NavigationBarCornerSize
                    else bottomPadding
                )
            )
        }
    }
}

@Composable
fun MovieBottomBar(
    destination: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    MovieNavigationBar(
        modifier = modifier,
        content = {
            destination.forEach { destination ->
                val selected = destination.isCurrentDestination(currentDestination)
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