package com.jik.lib.videoplayer

import androidx.media3.common.Player

object VideoUtil {
    const val VIDEO_URL_MOCK =
        "https://rr1---sn-3u-u5xs.googlevideo.com/videoplayback?expire=1692295125&ei=dQveZOnqAoKJ7OsPrvOv6Ak&ip=183.106.245.208&id=o-APlIV86iPQ141xkQXUPxvn3K_Hx5_7Qsl-h8JyH1SFri&itag=22&source=youtube&requiressl=yes&mh=np&mm=31%2C29&mn=sn-3u-u5xs%2Csn-3u-bh2s7&ms=au%2Crdu&mv=m&mvi=1&pl=19&initcwndbps=1267500&vprv=1&mime=video%2Fmp4&cnr=14&ratebypass=yes&dur=15.069&lmt=1689984903705333&mt=1692272950&fvip=8&fexp=24007246%2C24363391&beids=24350018&c=ANDROID_EMBEDDED_PLAYER&txp=5532434&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIhALkbJq_YRTxO3NgRHo5gGbjWY9YEHrQOjikKSAcDEI_7AiAcbAuQ2UUbBLV8rWfLw8R6n-iIH21x-SuMhuAu1TmdhQ%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRAIgNXWeKJaezoTTj9bR755SKks9pn_DVjFeYX3ztwqMCHMCIEe6MAvP7gecZvkKhJUtnHu7zMD0MvdGjyiH4kssHRPm"

    fun renderListener(playVideo: () -> Unit) = object : Player.Listener {
        override fun onRenderedFirstFrame() {
            playVideo()

            super.onRenderedFirstFrame()
        }
    }
}