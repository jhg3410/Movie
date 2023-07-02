package com.jik.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieInfo(
    val id: Long,
    val title: String,
    val genres: List<Genre>,
    val overview: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "vote_average") val rating: Double,
) {

    fun getReleaseDateString(): String = "release: $releaseDate"
    fun getPosterUrl() = "https://image.tmdb.org/t/p/w500$posterPath"

    @JsonClass(generateAdapter = true)
    data class Genre(
        val id: Int,
        val name: String
    )
}