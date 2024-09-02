package com.jik.lib.videoplayer.component.player

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jik.lib.videoplayer.component.iconSize

@Composable
fun PlayerLoadingWheel(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.size(iconSize)
    )
}