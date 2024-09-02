package com.jik.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Long,
    val title: String,
    @Json(name = "poster_path") val posterPath: String,
) {

    fun getPosterUrl() = "https://image.tmdb.org/t/p/w500$posterPath"

    companion object {
        val EMPTY = Movie(
            id = -1,
            title = "",
            posterPath = ""
        )
    }
}