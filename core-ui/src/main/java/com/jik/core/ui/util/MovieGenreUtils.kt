package com.jik.core.ui.util

import com.jik.core.ui.R

object MovieGenreUtils {

    fun getGenreColor(genre: String): Int = when (genre) {
        "Action" -> R.color.Action
        "Adventure" -> R.color.Adventure
        "Animation" -> R.color.Animation
        "Comedy" -> R.color.Comedy
        "Crime" -> R.color.Crime
        "Documentary" -> R.color.Documentary
        "Drama" -> R.color.Drama
        "Family" -> R.color.Family
        "Fantasy" -> R.color.Fantasy
        "History" -> R.color.History
        "Horror" -> R.color.Horror
        "Music" -> R.color.Music
        "Mystery" -> R.color.Mystery
        "Romance" -> R.color.Romance
        "Science Fiction" -> R.color.Science_Fiction
        "TV Movie" -> R.color.TV_Movie
        "Thriller" -> R.color.Thriller
        "War" -> R.color.War
        "Western" -> R.color.Western
        else -> R.color.Black
    }
}