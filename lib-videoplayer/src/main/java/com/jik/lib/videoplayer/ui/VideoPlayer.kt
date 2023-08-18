package com.jik.lib.videoplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.jik.lib.videoplayer.VideoPlayerIcons
import com.jik.lib.videoplayer.VideoUtil
import com.jik.lib.videoplayer.VideoUtil.toStreamUrlOfYouTube
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
        if (videoPlayerScreenVisible && player != null) {
            VideoPlayerScreen(player = player!!)
        } else {
            Thumbnail()
            VideoPlayerThumbnailPlayIcon {
                videoPlayerScreenVisible = true
            }
        }
    }
}

@Composable
fun VideoPlayerThumbnailPlayIcon(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.primary)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = VideoPlayerIcons.Play,
            contentDescription = null,
            tint = Color.White
        )
    }
}