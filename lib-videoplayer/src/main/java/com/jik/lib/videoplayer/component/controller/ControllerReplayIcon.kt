package com.jik.lib.videoplayer.component.controller

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jik.lib.videoplayer.component.VideoPlayerIcons.Refresh
import com.jik.lib.videoplayer.component.iconSize

@Composable
fun ControllerReplayIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            modifier = modifier.size(iconSize),
            imageVector = Refresh,
            contentDescription = "Replay",
            tint = Color.White,
        )
    }
}