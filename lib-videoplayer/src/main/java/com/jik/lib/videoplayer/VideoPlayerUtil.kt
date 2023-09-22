package com.jik.lib.videoplayer

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal object VideoPlayerUtil {

    suspend fun String.toStreamUrlOfYouTube(context: Context): String {
        val videoUrl = "https://www.youtube.com/watch?v=$this"

        return withContext(Dispatchers.IO) {
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(context))
            }

            val py = Python.getInstance()
            val module = py.getModule("youtube_stream_url")
            val result = module.callAttr("getVideoStreamUrl", videoUrl)

            result.toString()
        }
    }

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