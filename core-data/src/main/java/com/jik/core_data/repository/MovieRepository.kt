package com.jik.core_data.repository

interface MovieRepository {

    fun getPopularMovies()

    fun getMovieInfo(id: Int)
}