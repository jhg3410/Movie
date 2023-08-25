package com.jik.lib.videoplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jik.lib.videoplayer.component.VideoPlayerIcons.Refresh

@Composable
internal fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRefreshClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
        )
        if (onRefreshClick != null) {
            IconButton(
                onClick = {
                    onRefreshClick()
                },
            ) {
                Icon(
                    imageVector = Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White,
                )
            }
        }
    }
}