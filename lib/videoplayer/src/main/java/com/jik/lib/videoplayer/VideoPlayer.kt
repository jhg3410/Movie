package com.jik.lib.videoplayer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.jik.lib.videoplayer.component.player.PlayerLoadingWheel
import com.jik.lib.videoplayer.component.player.PlayerPlayIcon
import com.jik.lib.videoplayer.error.ErrorScreen
import com.jik.lib.videoplayer.error.VideoPlayerControllerError.setErrorMessageIfError
import com.jik.lib.videoplayer.error.VideoPlayerError.toMovieErrorMessage
import com.jik.lib.videoplayer.state.VideoPlayerControllerState
import com.jik.lib.videoplayer.state.VideoPlayerState
import com.jik.lib.videoplayer.state.getControllerState
import com.jik.lib.videoplayer.ui.VideoPlayerController
import com.jik.lib.videoplayer.ui.VideoPlayerScreen
import com.jik.lib.videoplayer.util.VideoPlayerControllerUtil.AUTO_HIDE_DELAY
import com.jik.lib.videoplayer.util.VideoPlayerListener.stateChangedListener
import com.jik.lib.videoplayer.util.VideoPlayerUtil.toStreamUrlOfYouTube
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
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
    val nonNullVideoId: String by rememberUpdatedState(newValue = videoId ?: "")

    var player: ExoPlayer? by remember { mutableStateOf(null) }
    var controllerVisible by remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableLongStateOf(0L) }
    val controllerEventChannel = remember { Channel<Unit>() }
    val controllerEventChannelFlow =
        remember(key1 = controllerEventChannel) { controllerEventChannel.receiveAsFlow() }
    var onControllerEvent: Long by remember { mutableLongStateOf(0) }

    var videoPlayerState: VideoPlayerState by remember { mutableStateOf(VideoPlayerState.Initial) }
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

    LaunchedEffect(key1 = controllerEventChannelFlow) {
        controllerEventChannelFlow.collectLatest {
            onControllerEvent += 1
        }
    }

    LaunchedEffect(key1 = controllerState, key2 = controllerVisible, key3 = onControllerEvent) {
        if (controllerState == VideoPlayerControllerState.PLAYING && controllerVisible) {
            delay(AUTO_HIDE_DELAY)
            controllerVisible = false
        }
    }


    LaunchedEffect(key1 = isPlaying) {
        while (isPlaying && player != null) {
            currentPosition = player?.currentPosition ?: 0L
            delay(1.seconds / 30)
        }
    }

    fun initializePlayer() {
        if (videoId == null) {
            videoPlayerState = VideoPlayerState.NoVideo()
            return
        }
        controllerVisible = true
        videoPlayerState = VideoPlayerState.Initial

        coroutineScope.launch {
            try {
                player = ExoPlayer.Builder(context).build().apply {
                    setMediaItem(
                        MediaItem.fromUri(streamUrl ?: nonNullVideoId.toStreamUrlOfYouTube(context)
                            .also { streamUrl = it }
                        ),
                        currentPosition
                    )
                    playWhenReady = false
                    addListener(stateChangedListener)
                    prepare()
                }
            } catch (e: Exception) {
                videoPlayerState =
                    VideoPlayerState.GetError(errorMessage = e.message.toMovieErrorMessage())
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

    LifecycleStartEffect(key1 = null) {
        initializePlayer()

        onStopOrDispose {
            releasePlayer()
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
                    it.volume = 0f
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
                        setErrorMessageIfError(errorCode = moviePlayer.playerError?.errorCode)
                    },
                    player = moviePlayer,
                    videoId = nonNullVideoId,
                    currentPosition = currentPosition,
                    visibleEventChannel = controllerEventChannel
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
                ErrorScreen(errorMessage = (videoPlayerState as VideoPlayerState.NoVideo).message)
            }
        }
    }
}