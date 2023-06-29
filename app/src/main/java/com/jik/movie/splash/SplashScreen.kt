package com.jik.movie.splash

import android.app.Activity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration

fun Activity.createSplashScreen(delay: Duration) {
    installSplashScreen().setKeepOnScreenCondition {
        runBlocking {
            delay(duration = delay)
        }
        false
    }
}