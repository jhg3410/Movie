package com.jik.lib.videoplayer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jik.lib.videoplayer.VideoPlayerControllerState
import com.jik.lib.videoplayer.VideoPlayerIcons.Forward5
import com.jik.lib.videoplayer.VideoPlayerIcons.Pause
import com.jik.lib.videoplayer.VideoPlayerIcons.Replay5
import com.jik.lib.videoplayer.iconSize

@Composable
fun VideoPlayerController(
    modifier: Modifier = Modifier,
    visible: Boolean,
    controllerState: VideoPlayerControllerState = VideoPlayerControllerState.PLAYING,
) {
    AnimatedVisibility(visible = visible) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.5f))
        ) {
            CenterController(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                controllerState = controllerState
            )
            BottomController(
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun CenterController(
    modifier: Modifier = Modifier,
    controllerState: VideoPlayerControllerState
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = Replay5,
                contentDescription = "Replay",
                tint = Color.White,
            )
        }

        // temporary
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = Pause,
                contentDescription = "Pause",
                tint = Color.White,
            )
        }

        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = Forward5,
                contentDescription = "Replay",
                tint = Color.White,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomController(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 4.dp)) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "00:21/02:23",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        var currentProgress by remember { mutableStateOf(0.2f) }

        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = currentProgress,
                    onValueChange = { currentProgress = it },
                    valueRange = 0f..1f,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = Color.Gray.copy(alpha = 0.5f),
                    )
                )
            }
        }
    }
}