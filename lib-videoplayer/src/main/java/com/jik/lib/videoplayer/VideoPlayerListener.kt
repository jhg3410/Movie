package com.jik.lib.videoplayer

import androidx.media3.common.Player

internal object VideoPlayerListener {

    fun stateChangedListener(onStateChanged: (Player) -> Unit) = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)

            onStateChanged(player)
        }
    }
}