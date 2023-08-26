package com.jik.lib.videoplayer.component.controller

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
    CircularProgressIndicator(
        color = Color.White,
        modifier = modifier.size(iconSize)
    )
}