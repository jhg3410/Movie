package com.jik.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id : Long,
    val title: String,
    @Json(name = "poster_path") val posterPath : String,
)