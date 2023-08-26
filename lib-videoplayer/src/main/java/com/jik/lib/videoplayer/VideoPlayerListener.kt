package com.jik.lib.videoplayer

import androidx.media3.common.Player

object VideoPlayerListener {

    fun renderFirstFrameListener(onRendered: () -> Unit) = object : Player.Listener {
        override fun onRenderedFirstFrame() {
            onRendered()

            super.onRenderedFirstFrame()
        }
    }

    fun stateChangedListener(onStateChanged: (Player) -> Unit) = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)

            onStateChanged(player)
        }
    }
}