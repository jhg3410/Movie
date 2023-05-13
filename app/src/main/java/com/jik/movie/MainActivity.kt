package com.jik.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jik.common.ui.component.MovieTopAppBar
import com.jik.common.ui.preview.DevicePreviews
import com.jik.common.ui.theme.MovieTheme
import com.jik.feature.popular.PopularScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
    Scaffold(
        topBar = { MovieTopAppBar(titleRes = R.string.app_name) }
    ) {
        PopularScreen(Modifier.padding(it))
    }
}


@DevicePreviews
@Composable
fun DefaultPreview() {
    MovieTheme {
        MovieApp()
    }
}