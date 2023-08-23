package com.jik.lib.videoplayer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.jik.lib.videoplayer.VideoPlayerState
import com.jik.lib.videoplayer.VideoPlayerUtil
import com.jik.lib.videoplayer.VideoPlayerUtil.toStreamUrlOfYouTube
import com.jik.lib.videoplayer.component.thumnail.ThumbnailLoadingWheel
import com.jik.lib.videoplayer.component.thumnail.ThumbnailPlayIcon
import kotlinx.coroutines.launch


@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    Thumbnail: @Composable () -> Unit,
    videoUrl: String?
) {
    val context = LocalContext.current

    var videoPlayerState: VideoPlayerState by remember { mutableStateOf(VideoPlayerState.Initial) }
    val coroutineScope = rememberCoroutineScope()
    var player: ExoPlayer? by remember { mutableStateOf(null) }
    val renderListener = VideoPlayerUtil.renderListener { player!!.play() }

    fun initializePlayer() {
        if (videoUrl == null) {
            videoPlayerState = VideoPlayerState.NoVideo
            return
        }
        coroutineScope.launch {
            try {
                player = ExoPlayer.Builder(context).build().apply {
                    setMediaItem(MediaItem.fromUri(videoUrl.toStreamUrlOfYouTube(context)))
                    addListener(renderListener)
                    prepare()
                }
            } catch (e: Exception) {
                videoPlayerState = VideoPlayerState.GetError(e.message ?: "Unknown Error")
            }
        }
    }

    fun releasePlayer() {
        player?.let {
            it.removeListener(renderListener)
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
                Thumbnail()
                ThumbnailPlayIcon {
                    videoPlayerState = VideoPlayerState.Loading
                }
            }
            is VideoPlayerState.Loading -> {
                Thumbnail()
                ThumbnailLoadingWheel()
                if (player != null) {
                    videoPlayerState = VideoPlayerState.CanPlay
                }
            }
            is VideoPlayerState.CanPlay -> {
                VideoPlayerScreen(player = player ?: return)
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