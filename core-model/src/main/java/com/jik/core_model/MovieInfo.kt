package com.jik.core_model

data class MovieInfo(
    val title: String,
    val rating: Double,
    val genres: List<String>,
    val overView: String,
)