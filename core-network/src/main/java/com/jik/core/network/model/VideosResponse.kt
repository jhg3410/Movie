package com.jik.core.network.model

import com.jik.core.model.MovieInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideosResponse(
    val id: Int,
    val results: List<MovieInfo.VideoInfo>
)