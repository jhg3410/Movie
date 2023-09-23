package com.jik.lib.videoplayer.error

internal object VideoPlayerError {

    enum class ErrorType(val message: String) {
        AGE_RESTRICTED_ERROR(message = "OMG!! this video requires login to watch"),
        URL_OPEN_ERROR(message = "please check network connection!!"),
        UNKNOWN_ERROR(message = "OMG!! this video is not available")
    }

    fun Exception.toMovieErrorMessage(): String {
        val errorMessage = message ?: ErrorType.UNKNOWN_ERROR.message

        return when {
            errorMessage.contains("AgeRestrictedError") -> {
                ErrorType.AGE_RESTRICTED_ERROR.message
            }

            errorMessage.contains("urlopen error") -> {
                ErrorType.URL_OPEN_ERROR.message
            }

            else -> {
                ErrorType.UNKNOWN_ERROR.message
            }
        }
    }
}