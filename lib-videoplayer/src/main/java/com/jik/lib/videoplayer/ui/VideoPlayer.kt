package com.jik.lib.videoplayer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.jik.lib.videoplayer.VideoPlayerControllerState
import com.jik.lib.videoplayer.VideoPlayerListener.stateChangedListener
import com.jik.lib.videoplayer.VideoPlayerState
import com.jik.lib.videoplayer.VideoPlayerUtil.toStreamUrlOfYouTube
import com.jik.lib.videoplayer.component.thumnail.ThumbnailLoadingWheel
import com.jik.lib.videoplayer.component.thumnail.ThumbnailPlayIcon
import com.jik.lib.videoplayer.getControllerState
import com.jik.lib.videoplayer.setErrorMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit,
    videoUrl: String?
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var videoPlayerState: VideoPlayerState by remember { mutableStateOf(VideoPlayerState.Initial) }
    var player: ExoPlayer? by remember { mutableStateOf(null) }

    var playWhenReady by remember { mutableStateOf(true) }
    var controllerVisible by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(false) }
    var playbackState by remember { mutableStateOf(0) }
    var currentPosition by remember { mutableStateOf(0L) }
    val controllerState = getControllerState(
        isPlaying = isPlaying,
        playbackState = playbackState
    )

    val stateChangedListener = stateChangedListener { changedPlayer ->
        playWhenReady = changedPlayer.playWhenReady
        isPlaying = changedPlayer.isPlaying
        playbackState = changedPlayer.playbackState
        currentPosition = changedPlayer.currentPosition
    }


    fun initializePlayer() {
        if (videoUrl == null) {
            videoPlayerState = VideoPlayerState.NoVideo
            return
        }
        coroutineScope.launch {
            try {
                player = ExoPlayer.Builder(context).build().apply {
                    setMediaItem(
                        MediaItem.fromUri(videoUrl.toStreamUrlOfYouTube(context)),
                        currentPosition
                    )
                    this.playWhenReady = playWhenReady
                    addListener(stateChangedListener)
                    prepare()
                }
            } catch (e: Exception) {
                videoPlayerState = VideoPlayerState.GetError(e.message ?: "Unknown Error")
            }
        }
    }

    fun releasePlayer() {
        player?.let {
            it.removeListener(stateChangedListener)
            it.release()
        }
        player = null
    }

    val lifeCycleOwner by rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(key1 = lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    initializePlayer()
                }

                Lifecycle.Event.ON_STOP -> {
                    releasePlayer()
                }

                else -> Unit
            }
        }

        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (isPlaying) {
        LaunchedEffect(key1 = Unit) {
            while (player != null) {
                currentPosition = player?.currentPosition ?: 0L
                delay(1.seconds / 30)
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (videoPlayerState) {
            is VideoPlayerState.Initial -> {
                thumbnail()
                ThumbnailPlayIcon {
                    videoPlayerState = VideoPlayerState.Loading
                }
            }

            is VideoPlayerState.Loading -> {
                thumbnail()
                ThumbnailLoadingWheel()
                if (player != null) {
                    videoPlayerState = VideoPlayerState.CanPlay
                }
            }

            is VideoPlayerState.CanPlay -> {
                VideoPlayerScreen(
                    player = player ?: return,
                    onScreenClick = { controllerVisible = !controllerVisible }
                )
                VideoPlayerController(
                    player = player ?: return,
                    modifier = Modifier.fillMaxSize(),
                    visible = controllerVisible,
                    controllerState = controllerState.apply {
                        if (this is VideoPlayerControllerState.ERROR) {
                            setErrorMessage(player ?: return)
                        }
                    },
                    currentPosition = currentPosition
                )
            }

            is VideoPlayerState.GetError -> {
                ErrorScreen(
                    errorMessage = (videoPlayerState as VideoPlayerState.GetError).errorMessage,
                    onRefreshClick = {
                        initializePlayer()
                        videoPlayerState = VideoPlayerState.Loading
                    }
                )
            }

            is VideoPlayerState.NoVideo -> {
                ErrorScreen(errorMessage = "No Video Found")
            }
        }
    }
}