package com.jik.movie.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.jik.core.designsystem.component.*
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.core.ui.R
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
            MovieNavHost(navController = appState.navController, modifier = Modifier.padding(it))
        }
    }
}

@Composable
fun MovieBottomBar(
    destination: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    MovieNavigationBar(
        modifier = modifier,
        content = {
            destination.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                MovieNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = destination.unselectedIcon,
                            contentDescription = null
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = destination.route
                        )
                    }
                )
            }
        }
    )
}


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return this?.hierarchy?.any { it.route == destination.route } ?: false
}