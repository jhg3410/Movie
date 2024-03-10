package com.jik.lib.videoplayer.error

internal object VideoPlayerError {

    private enum class ErrorType(val message: String) {
        AGE_RESTRICTED_ERROR(message = "OMG!! this video requires login to watch"),
        URL_OPEN_ERROR(message = "please check network connection!!"),
        UNKNOWN_ERROR(message = "OMG!! this video is not available")
    }

    fun String?.toMovieErrorMessage(): String {

        return when {
            this == null -> ErrorType.UNKNOWN_ERROR.message

            contains("AgeRestrictedError") -> {
                ErrorType.AGE_RESTRICTED_ERROR.message
            }

            contains("urlopen error") -> {
                ErrorType.URL_OPEN_ERROR.message
            }

            else -> {
                ErrorType.UNKNOWN_ERROR.message
            }
        }
    }
}