package com.jik.core.network.model

import com.jik.core.model.MovieInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditsResponse(
    val id: Long,
    val cast: List<MovieInfo.CastItem>,
)