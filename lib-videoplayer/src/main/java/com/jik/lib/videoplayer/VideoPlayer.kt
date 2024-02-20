package com.jik.lib.videoplayer

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
import com.jik.lib.videoplayer.component.player.PlayerLoadingWheel
import com.jik.lib.videoplayer.component.player.PlayerPlayIcon
import com.jik.lib.videoplayer.error.ErrorScreen
import com.jik.lib.videoplayer.error.VideoPlayerControllerError.setErrorMessage
import com.jik.lib.videoplayer.error.VideoPlayerError.toMovieErrorMessage
import com.jik.lib.videoplayer.state.VideoPlayerControllerState
import com.jik.lib.videoplayer.state.VideoPlayerState
import com.jik.lib.videoplayer.state.getControllerState
import com.jik.lib.videoplayer.ui.VideoPlayerController
import com.jik.lib.videoplayer.ui.VideoPlayerScreen
import com.jik.lib.videoplayer.util.VideoPlayerControllerUtil.VISIBLE_DURATION
import com.jik.lib.videoplayer.util.VideoPlayerListener.stateChangedListener
import com.jik.lib.videoplayer.util.VideoPlayerUtil.toStreamUrlOfYouTube
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    thumbnail: @Composable () -> Unit,
    videoId: String?
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var streamUrl: String? = remember { null }

    var player: ExoPlayer? by remember { mutableStateOf(null) }
    var videoPlayerState: VideoPlayerState by remember { mutableStateOf(VideoPlayerState.Initial) }

    var controllerVisible by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0L) }
    var controllerState: VideoPlayerControllerState by remember {
        mutableStateOf(VideoPlayerControllerState.INITIAL)
    }

    val stateChangedListener = stateChangedListener { changedPlayer ->
        isPlaying = changedPlayer.isPlaying
        currentPosition = changedPlayer.currentPosition
        controllerState = getControllerState(
            isPlaying = isPlaying,
            playbackState = changedPlayer.playbackState
        )
    }

    LaunchedEffect(key1 = controllerState, key2 = controllerVisible) {
        if (controllerState == VideoPlayerControllerState.PLAYING && controllerVisible) {
            delay(VISIBLE_DURATION)
            controllerVisible = false
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

    fun initializePlayer() {
        if (videoId == null) {
            videoPlayerState = VideoPlayerState.NoVideo
            return
        }
        controllerVisible = true
        videoPlayerState = VideoPlayerState.Initial

        coroutineScope.launch {
            try {
                player = ExoPlayer.Builder(context).build().apply {
                    setMediaItem(
                        MediaItem.fromUri(streamUrl ?: videoId.toStreamUrlOfYouTube(context).also {
                            streamUrl = it
                        }),
                        currentPosition
                    )
                    playWhenReady = false
                    addListener(stateChangedListener)
                    prepare()
                }
            } catch (e: Exception) {
                videoPlayerState = VideoPlayerState.GetError(e.toMovieErrorMessage())
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

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (videoPlayerState) {
            is VideoPlayerState.Initial -> {
                thumbnail()
                PlayerPlayIcon {
                    videoPlayerState = VideoPlayerState.Loading
                }
            }

            is VideoPlayerState.Loading -> {
                thumbnail()
                PlayerLoadingWheel()
                player?.let {
                    videoPlayerState = VideoPlayerState.CanPlay
                    it.play()
                }
            }

            is VideoPlayerState.CanPlay -> {
                val moviePlayer = player ?: return

                VideoPlayerScreen(
                    player = moviePlayer,
                    onScreenClick = { controllerVisible = !controllerVisible }
                )

                VideoPlayerController(
                    modifier = Modifier.fillMaxSize(),
                    visible = controllerVisible,
                    controllerState = controllerState.apply {
                        if (this is VideoPlayerControllerState.ERROR) {
                            this.setErrorMessage(errorCode = moviePlayer.playerError?.errorCode)
                        }
                    },
                    onRefresh = {
                        moviePlayer.prepare()
                        moviePlayer.play()
                    },
                    onPlay = moviePlayer::play,
                    onPause = moviePlayer::pause,
                    onReplay = moviePlayer::seekTo,
                    onForward = moviePlayer::seekTo,
                    onBackward = moviePlayer::seekTo,
                    getCurrentPosition = moviePlayer::getCurrentPosition,
                    currentPosition = currentPosition,
                    duration = moviePlayer.contentDuration,
                    bufferedPercentage = moviePlayer.bufferedPercentage,
                    onSlide = moviePlayer::seekTo
                )
            }

            is VideoPlayerState.GetError -> {
                ErrorScreen(
                    errorMessage = (videoPlayerState as VideoPlayerState.GetError).errorMessage,
                    onRefresh = {
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