package com.jik.lib.videoplayer.util

import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

internal object VideoPlayerControllerUtil {

    val VISIBLE_DURATION = 2.seconds
    val MOVING_OFFSET = 5.seconds.toLong(DurationUnit.MILLISECONDS)

    fun Long.toFormattedMinutesAndSecondsFromMilliseconds(): String {
        val timeInMilliseconds = this.coerceAtLeast(0)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds) % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}