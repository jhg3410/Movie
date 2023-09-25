package com.jik.lib.videoplayer.component.controller

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jik.lib.videoplayer.component.VideoPlayerIcons.Replay
import com.jik.lib.videoplayer.component.iconSize

@Composable
fun ControllerReplayIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, modifier = modifier.size(iconSize)) {
        Icon(
            modifier = modifier.fillMaxSize(),
            imageVector = Replay,
            contentDescription = "Replay",
            tint = Color.White,
        )
    }
}