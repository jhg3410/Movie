package com.jik.lib.videoplayer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import com.jik.lib.videoplayer.VideoPlayerControllerState
import com.jik.lib.videoplayer.component.VideoPlayerIcons.Forward5
import com.jik.lib.videoplayer.component.VideoPlayerIcons.Replay5
import com.jik.lib.videoplayer.component.controller.ControllerLoadingWheel
import com.jik.lib.videoplayer.component.controller.ControllerPauseIcon
import com.jik.lib.videoplayer.component.controller.ControllerPlayIcon
import com.jik.lib.videoplayer.component.controller.ControllerReplayIcon
import com.jik.lib.videoplayer.component.iconSize

@Composable
fun VideoPlayerController(
    player: ExoPlayer,
    modifier: Modifier = Modifier,
    visible: Boolean,
    controllerState: VideoPlayerControllerState
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        if (controllerState is VideoPlayerControllerState.ERROR) {
            ErrorScreen(
                errorMessage = controllerState.errorMessage,
                onRefreshClick = {
                    player.prepare()
                    player.play()
                }
            )

            return@AnimatedVisibility
        }
        Box(
            modifier = Modifier.background(color = Color.Black.copy(alpha = 0.5f))
        ) {
            CenterController(
                player = player,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                controllerState = controllerState
            )
            BottomController(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun CenterController(
    player: ExoPlayer,
    modifier: Modifier = Modifier,
    controllerState: VideoPlayerControllerState,
    onReplayClick: () -> Unit = {},
    onForwardClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = onReplayClick
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = Replay5,
                contentDescription = "Replay",
                tint = Color.White,
            )
        }

        when (controllerState) {
            is VideoPlayerControllerState.LOADING -> {
                ControllerLoadingWheel()
            }
            is VideoPlayerControllerState.PLAYING -> {
                ControllerPauseIcon {
                    player.pause()
                }
            }
            is VideoPlayerControllerState.PAUSED -> {
                ControllerPlayIcon {
                    player.play()
                }
            }
            is VideoPlayerControllerState.ENDED -> {
                ControllerReplayIcon {
                    player.seekTo(0)
                }
            }
            is VideoPlayerControllerState.ERROR -> Unit
        }

        IconButton(
            onClick = onForwardClick
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