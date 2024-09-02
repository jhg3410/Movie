package com.jik.lib.videoplayer.util

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal object VideoPlayerUtil {

    private const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
    private const val PYTHON_FILE_NAME = "youtube_stream_url"
    private const val PYTHON_FUNCTION_NAME = "getVideoStreamUrl"

    suspend fun String.toStreamUrlOfYouTube(context: Context): String {
        val videoUrl = "$YOUTUBE_BASE_URL$this"

        return withContext(Dispatchers.IO) {
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(context))
            }

            val py = Python.getInstance()
            val module = py.getModule(PYTHON_FILE_NAME)
            val result = module.callAttr(PYTHON_FUNCTION_NAME, videoUrl)

            result.toString()
        }
    }
}