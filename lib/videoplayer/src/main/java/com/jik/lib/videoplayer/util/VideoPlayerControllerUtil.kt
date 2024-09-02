package com.jik.lib.videoplayer.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

internal object VideoPlayerControllerUtil {

    val AUTO_HIDE_DELAY = 2.seconds
    val MOVING_OFFSET = 5.seconds.toLong(DurationUnit.MILLISECONDS)

    fun Long.toFormattedMinutesAndSecondsFromMilliseconds(): String {
        val timeInMilliseconds = this.coerceAtLeast(0)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMilliseconds)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds) % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    internal fun watchOnYoutube(context: Context, videoId: String, currentTime: Long) {
        val appUrl = "vnd.youtube:$videoId?t=$currentTime"
        val webUrl = "http://www.youtube.com/watch?v=$videoId&t=$currentTime"

        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(appIntent)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }


    internal object KeepVisible {
        lateinit var visibleEventChannel: Channel<Unit>
        lateinit var scope: CoroutineScope

        operator fun invoke(block: () -> Unit) {
            scope.launch {
                visibleEventChannel.send(Unit)
            }
            block()
        }
    }
}