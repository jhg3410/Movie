package com.jik.lib.videoplayer.error

import androidx.media3.common.PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED
import com.jik.lib.videoplayer.state.VideoPlayerControllerState

internal object VideoPlayerControllerError {

    enum class ErrorType(val message: String) {
        NETWORK_CONNECTION_ERROR(message = "please check network connection!!"),
        UNKNOWN_ERROR(message = "unknown error has occurred!!")
    }

    fun VideoPlayerControllerState.setErrorMessageIfError(errorCode: Int?) {
        (this as? VideoPlayerControllerState.ERROR)?.errorMessage =
            if (errorCode == ERROR_CODE_IO_NETWORK_CONNECTION_FAILED) {
                ErrorType.NETWORK_CONNECTION_ERROR.message
            } else {
                ErrorType.UNKNOWN_ERROR.message
            }
    }
}