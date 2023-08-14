package com.jik.lib.videoplayer.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.jik.lib.videoplayer.VideoPlayerIcons

@Composable
fun VideoPlayerThumbnail(
    Thumbnail: @Composable () -> Unit,
) {
    Box(contentAlignment = Alignment.Center) {
        Thumbnail()
        VideoPlayerThumbnailPlayIcon()
    }
}

@Composable
fun VideoPlayerThumbnailPlayIcon() {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = VideoPlayerIcons.Play,
            contentDescription = null,
            tint = Color.White
        )
    }
}