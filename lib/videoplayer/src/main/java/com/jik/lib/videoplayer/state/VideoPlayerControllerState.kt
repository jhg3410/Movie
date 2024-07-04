package com.jik.lib.videoplayer.state

import androidx.media3.exoplayer.ExoPlayer
import com.jik.lib.videoplayer.error.VideoPlayerControllerError.ErrorType as ControllerErrorType

sealed interface VideoPlayerControllerState {
    object INITIAL : VideoPlayerControllerState

    object LOADING : VideoPlayerControllerState

    object PLAYING : VideoPlayerControllerState

    object PAUSED : VideoPlayerControllerState

    object ENDED : VideoPlayerControllerState

    class ERROR(var errorMessage: String = ControllerErrorType.UNKNOWN_ERROR.message) :
        VideoPlayerControllerState
}


internal fun getControllerState(
    isPlaying: Boolean,
    playbackState: Int
): VideoPlayerControllerState {
    if (isPlaying) {
        return VideoPlayerControllerState.PLAYING
    }
    if (playbackState == ExoPlayer.STATE_IDLE) {
        return VideoPlayerControllerState.ERROR()
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

    return VideoPlayerControllerState.ERROR()
}