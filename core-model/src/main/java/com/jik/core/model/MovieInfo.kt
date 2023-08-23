package com.jik.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieInfo(
    val id: Long,
    val title: String,
    val genres: List<Genre>,
    val overview: String,
    @Json(ignore = true) val cast: List<CastItem> = emptyList(),
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "vote_average") val rating: Double,
) {

    fun getReleaseDateString(): String = "release: $releaseDate"
    fun getPosterUrl() = "https://image.tmdb.org/t/p/w500$posterPath"
    fun getBackdropUrl() = "https://image.tmdb.org/t/p/w500$backdropPath"


    @JsonClass(generateAdapter = true)
    data class Genre(
        val name: String
    )

    @JsonClass(generateAdapter = true)
    data class CastItem(
        val name: String,
        val character: String,
        @Json(name = "profile_path") val profilePath: String?,
        @Json(name = "known_for_department") val knownForDepartment: String,
    ) {
        fun getProfileUrl() = "https://image.tmdb.org/t/p/w500$profilePath"
    }
}