package com.jik.core.ui.util

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowInsetsCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun StatusBarColor(color: Color = MaterialTheme.colorScheme.primary) {
    val systemUiController = rememberSystemUiController()
    val darkIcons = isSystemInDarkTheme().not()

    SideEffect {
        systemUiController.setStatusBarColor(color, darkIcons = darkIcons)
    }
}

@Composable
fun getStatusBarHeight(): Dp {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val insets = window.decorView.rootWindowInsets

    val statusBarHeight =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
    } else {
        insets?.systemWindowInsetTop ?: 0
    }

    return statusBarHeight.pxToDp()
}