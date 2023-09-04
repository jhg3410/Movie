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
import com.jik.lib.videoplayer.VideoPlayerUtil.toFormattedMinutesAndSecondsFromMilliseconds
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
    controllerState: VideoPlayerControllerState,
    currentPosition: Long,
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
                player = player,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                currentPosition = currentPosition,
            )
        }
    }
}

@Composable
fun CenterController(
    player: ExoPlayer,
    modifier: Modifier = Modifier,
    controllerState: VideoPlayerControllerState,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                player.seekTo(player.currentPosition - 5_000)
            }
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
            onClick = {
                player.seekTo(player.currentPosition + 5_000)
            }
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
    player: ExoPlayer,
    modifier: Modifier = Modifier,
    currentPosition: Long
) {
    Column(modifier = modifier.padding(bottom = 4.dp)) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = currentPosition.toFormattedMinutesAndSecondsFromMilliseconds() + "/" +
                    player.contentDuration.toFormattedMinutesAndSecondsFromMilliseconds(),
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = player.bufferedPercentage.toFloat(),
                    enabled = false,
                    onValueChange = {},
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        disabledThumbColor = Color.Transparent,
                        disabledActiveTrackColor = Color.Gray
                    )
                )

                Slider(
                    value = currentPosition.toFloat(),
                    onValueChange = {
                        player.seekTo(it.toLong())
                    },
                    valueRange = 0f..player.contentDuration.coerceAtLeast(0).toFloat(),
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