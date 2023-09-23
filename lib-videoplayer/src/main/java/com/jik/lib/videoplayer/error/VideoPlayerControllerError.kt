package com.jik.lib.videoplayer.error

import androidx.media3.common.Player
import com.jik.lib.videoplayer.state.VideoPlayerControllerState

internal object VideoPlayerControllerError {

    fun VideoPlayerControllerState.setErrorMessage(player: Player) {
        (this as? VideoPlayerControllerState.ERROR)?.errorMessage =
            player.playerError?.message ?: "Unknown Error"
    }
}