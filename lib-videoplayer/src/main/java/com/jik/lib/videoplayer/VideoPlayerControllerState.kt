package com.jik.lib.videoplayer

import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

sealed interface VideoPlayerControllerState {

    object LOADING : VideoPlayerControllerState

    object PLAYING : VideoPlayerControllerState

    object PAUSED : VideoPlayerControllerState

    object ENDED : VideoPlayerControllerState

    class ERROR(var errorMessage: String) : VideoPlayerControllerState
}


internal fun getControllerState(
    isPlaying: Boolean,
    playbackState: Int
): VideoPlayerControllerState {
    if (isPlaying) {
        return VideoPlayerControllerState.PLAYING
    }
    if (playbackState == ExoPlayer.STATE_IDLE) {
        return VideoPlayerControllerState.ERROR("Unknown Error")
    }
    if (playbackState == ExoPlayer.STATE_BUFFERING) {
        return VideoPlayerControllerState.LOADING
    }
    if (playbackState == ExoPlayer.STATE_READY) {
        return VideoPlayerControllerState.PAUSED
    }
    if (playbackState == ExoPlayer.STATE_ENDED) {
        return VideoPlayerControllerState.ENDED
    }

    return VideoPlayerControllerState.ERROR("Unknown Error")
}


internal fun VideoPlayerControllerState.setErrorMessage(player: Player) {
    (this as? VideoPlayerControllerState.ERROR)?.errorMessage =
        player.playerError?.message ?: "Unknown Error"
}