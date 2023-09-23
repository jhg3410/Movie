package com.jik.lib.videoplayer

import java.util.concurrent.TimeUnit

internal object VideoPlayerControllerUtil {

    fun Long.toFormattedMinutesAndSecondsFromMilliseconds(): String {
        val timeInMilliseconds = this.coerceAtLeast(0)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds) % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}