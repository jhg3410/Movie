package com.jik.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jik.common.ui.component.MovieTopAppBar
import com.jik.common.ui.theme.MovieTheme
import com.jik.feature.popular.PopularScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieTheme(
                dynamicColor = false
            ) {
                MovieApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    Scaffold(
        topBar = { MovieTopAppBar(titleRes = R.string.app_name) }
    ) {
        PopularScreen(Modifier.padding(it))
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieTheme {
        MovieApp()
    }
}