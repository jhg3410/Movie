package com.jik.lib.videoplayer.component.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.jik.lib.videoplayer.component.VideoPlayerIcons.Play
import com.jik.lib.videoplayer.component.iconSize

@Composable
fun PlayerPlayIcon(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(iconSize)
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.primary)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Play,
            contentDescription = null,
            tint = Color.White
        )
    }
}