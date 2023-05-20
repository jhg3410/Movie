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
import com.jik.common.ui.component.MovieTopAppBar
import com.jik.common.ui.theme.MovieTheme
import com.jik.feature.popular.PopularScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        createSplashScreen(delay = 1.2.seconds)

        super.onCreate(savedInstanceState)
        setContent {
            MovieTheme(
                dynamicColor = false
            ) {
                MovieScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MovieTopAppBar(titleRes = R.string.app_name, scrollBehavior = scrollBehavior) }
    ) {
        PopularScreen(Modifier.padding(it))
    }
}
