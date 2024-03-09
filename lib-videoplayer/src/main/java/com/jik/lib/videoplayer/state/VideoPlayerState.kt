package com.jik.lib.videoplayer.state

sealed interface VideoPlayerState {

    object Initial : VideoPlayerState
    object Loading : VideoPlayerState
    object CanPlay : VideoPlayerState
    class GetError(val errorMessage: String) : VideoPlayerState
    class NoVideo(val message: String = "Sorry, there's no video") : VideoPlayerState
}