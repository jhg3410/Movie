package com.jik.core_model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieInfo(
    val id: Int,
    val title: String,
    val genres: List<Genre>,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "vote_average") val rating: Double,
) {

    @JsonClass(generateAdapter = true)
    data class Genre(
        val id: Int,
        val name: String
    )
}