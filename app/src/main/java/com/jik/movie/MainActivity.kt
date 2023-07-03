package com.jik.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jik.movie.splash.createSplashScreen
import com.jik.movie.ui.MovieApp
import dagger.hilt.android.AndroidEntryPoint
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