package com.jik.lib.videoplayer.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeOff
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.filled.Forward5
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay5
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.ui.unit.dp

internal object VideoPlayerIcons {

    val Play = Icons.Filled.PlayArrow
    val Pause = Icons.Rounded.Pause
    val Replay = Icons.Rounded.Refresh
    val Refresh = Icons.Rounded.Refresh

    val Forward5 = Icons.Filled.Forward5
    val Backward5 = Icons.Filled.Replay5

    val VolumeUp = Icons.AutoMirrored.Rounded.VolumeUp
    val VolumeOFF = Icons.AutoMirrored.Rounded.VolumeOff
}

internal val iconSize = 40.dp