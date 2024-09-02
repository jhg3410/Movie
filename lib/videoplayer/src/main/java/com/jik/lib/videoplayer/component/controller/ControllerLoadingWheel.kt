package com.jik.lib.videoplayer.component.controller

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jik.lib.videoplayer.component.iconSize

@Composable
fun ControllerLoadingWheel(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(iconSize)) {
        CircularProgressIndicator(
            color = Color.White,
            modifier = modifier.fillMaxSize()
        )
    }
}