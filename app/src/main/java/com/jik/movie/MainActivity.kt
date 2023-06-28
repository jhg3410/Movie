package com.jik.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.rememberNavController
import com.jik.core.designsystem.component.MovieTopAppBar
import com.jik.core.designsystem.theme.MovieTheme
import com.jik.movie.navigation.MovieNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        createSplashScreen(delay = 1.2.seconds)

        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    MovieTheme(
        dynamicColor = false
    ) {
        val navController = rememberNavController()

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MovieTopAppBar(
                    titleRes = R.string.app_name,
                    scrollBehavior = scrollBehavior
                )
            }
        ) {
            MovieNavHost(navController = navController, modifier = Modifier.padding(it))
        }
    }
}