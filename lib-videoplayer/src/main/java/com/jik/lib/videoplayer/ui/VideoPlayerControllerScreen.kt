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
import com.jik.lib.videoplayer.component.VideoPlayerIcons.Backward5
import com.jik.lib.videoplayer.component.VideoPlayerIcons.Forward5
import com.jik.lib.videoplayer.component.controller.ControllerLoadingWheel
import com.jik.lib.videoplayer.component.controller.ControllerPauseIcon
import com.jik.lib.videoplayer.component.controller.ControllerPlayIcon
import com.jik.lib.videoplayer.component.controller.ControllerReplayIcon
import com.jik.lib.videoplayer.component.iconSize
import com.jik.lib.videoplayer.error.ErrorScreen
import com.jik.lib.videoplayer.state.VideoPlayerControllerState
import com.jik.lib.videoplayer.util.VideoPlayerControllerUtil.MOVING_OFFSET
import com.jik.lib.videoplayer.util.VideoPlayerControllerUtil.toFormattedMinutesAndSecondsFromMilliseconds

@Composable
fun VideoPlayerController(
    modifier: Modifier = Modifier,
    visible: Boolean,
    controllerState: VideoPlayerControllerState,
    onRefresh: () -> Unit,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onReplay: (Long) -> Unit,
    onForward: (Long) -> Unit,
    onBackward: (Long) -> Unit,
    getCurrentPosition: () -> Long,
    currentPosition: Long,
    duration: Long,
    bufferedPercentage: Int,
    onSlide: (Long) -> Unit
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
                onRefreshClick = onRefresh
            )
            return@AnimatedVisibility
        }
        Box(
            modifier = Modifier.background(color = Color.Black.copy(alpha = 0.5f))
        ) {
            CenterController(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                controllerState = controllerState,
                onPlay = onPlay,
                onPause = onPause,
                onReplay = onReplay,
                onForward = { onForward(getCurrentPosition() + MOVING_OFFSET) },
                onBackward = { onBackward(getCurrentPosition() - MOVING_OFFSET) }
            )
            BottomController(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                currentPosition = currentPosition,
                duration = duration,
                bufferedPercentage = bufferedPercentage,
                onSlide = onSlide
            )
        }
    }
}

@Composable
fun CenterController(
    modifier: Modifier = Modifier,
    controllerState: VideoPlayerControllerState,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onReplay: (Long) -> Unit,
    onForward: () -> Unit,
    onBackward: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackward() }
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = Backward5,
                contentDescription = "backward",
                tint = Color.White,
            )
        }

        when (controllerState) {
            is VideoPlayerControllerState.LOADING -> {
                ControllerLoadingWheel()
            }

            is VideoPlayerControllerState.PLAYING -> {
                ControllerPauseIcon {
                    onPause()
                }
            }

            is VideoPlayerControllerState.PAUSED -> {
                ControllerPlayIcon {
                    onPlay()
                }
            }

            is VideoPlayerControllerState.ENDED -> {
                ControllerReplayIcon {
                    onReplay(0L)
                }
            }

            else -> Unit
        }

        IconButton(
            onClick = { onForward() }
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = Forward5,
                contentDescription = "Forward",
                tint = Color.White,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomController(
    modifier: Modifier = Modifier,
    duration: Long,
    currentPosition: Long,
    bufferedPercentage: Int,
    onSlide: (Long) -> Unit
) {
    Column(modifier = modifier.padding(bottom = 4.dp)) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = currentPosition.toFormattedMinutesAndSecondsFromMilliseconds() + "/" +
                    duration.toFormattedMinutesAndSecondsFromMilliseconds(),
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Slider(
                    value = bufferedPercentage.toFloat(),
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
                    onValueChange = { onSlide(it.toLong()) },
                    valueRange = 0f..duration.coerceAtLeast(0).toFloat(),
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