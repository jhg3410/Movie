package com.jik.movie.splash

import android.app.Activity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun Activity.showSplashScreenWithDelay(delay: Duration = 1.2.seconds) {
    installSplashScreen().setKeepOnScreenCondition {
        CoroutineScope(Dispatchers.Main).launch {
            delay(duration = delay)
        }
        false
    }
}