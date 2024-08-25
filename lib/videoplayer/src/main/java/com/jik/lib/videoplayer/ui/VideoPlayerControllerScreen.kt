package com.jik.lib.videoplayer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import com.jik.lib.videoplayer.R
import com.jik.lib.videoplayer.component.VideoPlayerIcons
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
import com.jik.lib.videoplayer.util.VideoPlayerControllerUtil.watchOnYoutube
import kotlinx.coroutines.channels.Channel
import com.jik.lib.videoplayer.util.VideoPlayerControllerUtil as controllerUtil

@Composable
fun VideoPlayerController(
    modifier: Modifier = Modifier,
    visible: Boolean,
    controllerState: VideoPlayerControllerState,
    player: ExoPlayer,
    videoId: String,
    currentPosition: Long,
    visibleEventChannel: Channel<Unit>
) {

    val moviePlayer by rememberUpdatedState(newValue = player)
    val coroutineScope = rememberCoroutineScope()
    var isMute by remember { mutableStateOf(moviePlayer.volume == 0f) }

    LaunchedEffect(key1 = visibleEventChannel) {
        controllerUtil.KeepVisible.visibleEventChannel = visibleEventChannel
        controllerUtil.KeepVisible.scope = coroutineScope
    }


    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        if (controllerState is VideoPlayerControllerState.ERROR) {
            ErrorScreen(
                errorMessage = controllerState.errorMessage,
                onRefresh = {
                    moviePlayer.prepare()
                    moviePlayer.play()
                }
            )
            return@AnimatedVisibility
        }
        Box(
            modifier = Modifier.background(color = Color.Black.copy(alpha = 0.5f))
        ) {
            TopController(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth(),
                videoId = videoId
            )
            CenterController(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                controllerState = controllerState,
                onPlay = { moviePlayer.play() },
                onPause = { moviePlayer.pause() },
                onReplay = { moviePlayer.seekTo(0L) },
                onForward = { moviePlayer.seekTo(moviePlayer.currentPosition + MOVING_OFFSET) },
                onBackward = { moviePlayer.seekTo(moviePlayer.currentPosition - MOVING_OFFSET) }
            )
            BottomController(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                currentPosition = currentPosition,
                duration = moviePlayer.duration,
                bufferedPercentage = moviePlayer.bufferedPercentage,
                onSlide = { moviePlayer.seekTo(it) },
                toggleMute = controllerUtil.KeepVisible {
                    moviePlayer.volume = if (isMute) 1f else 0f
                    isMute = isMute.not()
                },
                isMute = isMute
            )
        }
    }
}

@Composable
fun TopController(
    modifier: Modifier = Modifier,
    videoId: String
) {
    val context = LocalContext.current

    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = { watchOnYoutube(context = context, videoId = videoId) }
        ) {
            Icon(
                modifier = Modifier.height(24.dp),
                painter = painterResource(id = R.drawable.youtube_icon),
                contentDescription = "Watch on Youtube",
                tint = Color.Unspecified,
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
    onReplay: () -> Unit,
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
                    onReplay()
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
    onSlide: (Long) -> Unit,
    toggleMute: () -> Unit,
    isMute: Boolean
) {
    Column(modifier = modifier.padding(bottom = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = currentPosition.toFormattedMinutesAndSecondsFromMilliseconds() + "/" +
                        duration.toFormattedMinutesAndSecondsFromMilliseconds(),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
            IconButton(
                modifier = Modifier.size(32.dp),
                onClick = { toggleMute() }
            ) {
                Icon(
                    imageVector = if (isMute) VideoPlayerIcons.VolumeOFF else VideoPlayerIcons.VolumeUp,
                    contentDescription = if (isMute) "Volume Off" else "Volume On",
                    tint = Color.White,
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
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