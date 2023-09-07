package com.jik.lib.videoplayer

sealed interface VideoPlayerState {

    object Initial : VideoPlayerState
    object Loading : VideoPlayerState
    object CanPlay : VideoPlayerState
    class GetError(val errorMessage: String) : VideoPlayerState
    object NoVideo : VideoPlayerState
}