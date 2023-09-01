package com.jik.lib.videoplayer

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

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

    fun Long.toFormattedMinutesAndSecondsFromMilliseconds(): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}