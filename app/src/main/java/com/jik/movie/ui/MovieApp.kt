package com.jik.movie.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.jik.core.designsystem.component.MovieTopAppBar
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.core.ui.R
import com.jik.movie.navigation.MovieNavHost

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
            contentWindowInsets = WindowInsets(0.dp)
        ) {
            MovieNavHost(navController = appState.navController, modifier = Modifier.padding(it))
        }
    }
}