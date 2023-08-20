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
import com.jik.lib.videoplayer.VideoUtil
import com.jik.lib.videoplayer.VideoUtil.toStreamUrlOfYouTube
import com.jik.lib.videoplayer.component.thumnail.ThumbnailLoadingWheel
import com.jik.lib.videoplayer.component.thumnail.ThumbnailPlayIcon
import kotlinx.coroutines.launch


@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    Thumbnail: @Composable () -> Unit,
    videoUrl: String
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var player: ExoPlayer? by remember { mutableStateOf(null) }
    var videoPlayerScreenVisible by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    val renderListener = VideoUtil.renderListener {
        player!!.play()
    }

    fun initializePlayer() {
        coroutineScope.launch {
            player = ExoPlayer.Builder(context).build()
                .also { exoPlayer ->
                    exoPlayer.setMediaItem(MediaItem.fromUri(videoUrl.toStreamUrlOfYouTube(context)))
                    exoPlayer.addListener(renderListener)
                    exoPlayer.prepare()
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
        if (videoPlayerScreenVisible.not() || player == null) {
            Thumbnail()
            if (loading.not()) {
                ThumbnailPlayIcon {
                    loading = true
                    videoPlayerScreenVisible = true
                }
            } else {
                ThumbnailLoadingWheel()
            }
        } else {
            VideoPlayerScreen(player = player!!)
        }
    }
}
