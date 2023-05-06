package com.jik.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieInfo(
    val id: Long,
    val title: String,
    val genres: List<Genre>,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "vote_average") val rating: Double,
) {

    fun getGenreString(): List<String> = genres.map { it.name }

    @JsonClass(generateAdapter = true)
    data class Genre(
        val id: Int,
        val name: String
    )
}